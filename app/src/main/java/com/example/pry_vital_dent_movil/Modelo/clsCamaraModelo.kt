package com.example.pry_vital_dent_movil.Modelo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Base64
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.pry_vital_dent_movil.R
import java.io.ByteArrayOutputStream
import java.io.File

// ACC-08 (RNF-02): foto de perfil tomada con la cámara del celular.
// La foto se guarda en la base de datos (tabla fotos_perfil) con la API foto_perfil.php de APIs-VD.
class clsCamaraModelo(private val contexto: Context) {

    private val url = RetrofitClient.URL_CORREOS + "foto_perfil.php"
    private val hiloPrincipal = Handler(Looper.getMainLooper())

    companion object {
        const val TAMANO_FOTO = 512   // la foto se guarda cuadrada de 512 x 512 px
        const val CALIDAD_JPG = 85
    }

    // Archivo temporal donde la app de cámara guarda la foto (se comparte con FileProvider)
    fun CrearArchivoParaFoto(): Uri {
        val carpeta = File(contexto.cacheDir, "fotos").apply { mkdirs() }
        val archivo = File(carpeta, "foto_perfil.jpg")
        if (archivo.exists()) archivo.delete()
        return FileProvider.getUriForFile(contexto, contexto.packageName + ".fileprovider", archivo)
    }

    // Lee la foto que tomó la cámara, la endereza, la achica y la pone completa en un cuadro.
    // Se hace en segundo plano porque la foto original puede pesar varios MB.
    fun PrepararFoto(uri: Uri, alTerminar: (Bitmap?) -> Unit) {
        Thread {
            val foto = try { LeerYAjustar(uri) } catch (e: Exception) { null }
            hiloPrincipal.post { alTerminar(foto) }
        }.start()
    }

    // GET foto_perfil.php: la foto guardada (null si no tiene o si falla la conexión)
    fun ObtenerFoto(alTerminar: (Bitmap?) -> Unit) {
        val token = clsSesion.ObtenerToken(contexto)
        if (token.isEmpty()) {
            alTerminar(null)
            return
        }
        val llamada = RetrofitClient.api.ObtenerFotoPerfil(url, token)
        RetrofitClient.Ejecutar(contexto, llamada) { exito, _, datos ->
            val base64 = datos?.foto
            if (!exito || base64.isNullOrEmpty()) {
                alTerminar(null)
                return@Ejecutar
            }
            val bytes = try { Base64.decode(base64, Base64.DEFAULT) } catch (e: IllegalArgumentException) { null }
            alTerminar(bytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) })
        }
    }

    // POST foto_perfil.php: guarda la foto como JPG en base64
    fun GuardarFoto(foto: Bitmap, alTerminar: (Boolean, String) -> Unit) {
        val salida = ByteArrayOutputStream()
        foto.compress(Bitmap.CompressFormat.JPEG, CALIDAD_JPG, salida)
        val base64 = Base64.encodeToString(salida.toByteArray(), Base64.NO_WRAP)

        val llamada = RetrofitClient.api.GuardarFotoPerfil(url, clsSesion.ObtenerToken(contexto), SolicitudFotoPerfil(base64))
        RetrofitClient.Ejecutar(contexto, llamada) { exito, mensaje, _ ->
            alTerminar(exito, mensaje)
        }
    }

    private fun LeerYAjustar(uri: Uri): Bitmap? {
        val resolver = contexto.contentResolver

        // 1. Medidas de la foto sin cargarla completa
        val medidas = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, medidas) }
        if (medidas.outWidth <= 0 || medidas.outHeight <= 0) return null

        // 2. Cargarla reducida (potencia de 2) para no llenar la memoria
        var reduccion = 1
        while (minOf(medidas.outWidth, medidas.outHeight) / (reduccion * 2) >= TAMANO_FOTO) {
            reduccion *= 2
        }
        val opciones = BitmapFactory.Options().apply { inSampleSize = reduccion }
        val original = resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, opciones) }
            ?: return null

        // 3. Girarla según cómo se sostuvo el celular (dato EXIF de la cámara)
        val orientacion = resolver.openInputStream(uri)?.use {
            ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        } ?: ExifInterface.ORIENTATION_NORMAL
        val grados = when (orientacion) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

        // 4. Girarla y achicarla para que el lado más largo mida TAMANO_FOTO
        val escala = TAMANO_FOTO.toFloat() / maxOf(original.width, original.height)
        val matriz = Matrix().apply {
            postScale(escala, escala)
            postRotate(grados)
        }
        val girada = Bitmap.createBitmap(original, 0, 0, original.width, original.height, matriz, true)
        if (girada != original) original.recycle()

        // 5. Ponerla completa (sin recortar) al centro de un cuadro de TAMANO_FOTO.
        //    Antes se recortaba el cuadro del centro y se perdían la frente y la barbilla;
        //    ahora se ve toda la cara y lo que sobra se rellena con el color del círculo.
        val resultado = Bitmap.createBitmap(TAMANO_FOTO, TAMANO_FOTO, Bitmap.Config.ARGB_8888)
        val lienzo = Canvas(resultado)
        lienzo.drawColor(ContextCompat.getColor(contexto, R.color.vd_placeholder))
        lienzo.drawBitmap(
            girada,
            (TAMANO_FOTO - girada.width) / 2f,
            (TAMANO_FOTO - girada.height) / 2f,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )
        girada.recycle()
        return resultado
    }
}
