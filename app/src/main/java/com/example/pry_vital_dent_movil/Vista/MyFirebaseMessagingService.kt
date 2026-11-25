package com.example.pry_vital_dent_movil.Vista

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.pry_vital_dent_movil.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.pry_vital_dent_movil.Modelo.clsNotificacionesModelo


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCM_VitalDent"
        const val CANAL_ID = "vitaldent_general"

        fun crearCanal(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val canal = NotificationChannel(
                    CANAL_ID,
                    "Avisos de Vital Dent",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Citas, pagos y avisos de la clínica"
                }
                context.getSystemService(NotificationManager::class.java)
                    ?.createNotificationChannel(canal)
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "Nuevo token FCM: $token")
        clsNotificacionesModelo.RegistrarTokenNuevo(applicationContext, token)
    }

    override fun onMessageReceived(mensaje: RemoteMessage) {
        val titulo = mensaje.notification?.title ?: mensaje.data["titulo"] ?: "Vital Dent"
        val cuerpo = mensaje.notification?.body ?: mensaje.data["mensaje"] ?: ""

        Log.e(TAG, "Notificación recibida: $titulo - $cuerpo")
        mostrarNotificacion(titulo, cuerpo)
    }

    private fun mostrarNotificacion(titulo: String, cuerpo: String) {
        crearCanal(this)

        // Al tocarla se abre MainActivity; esa pantalla ya decide si va al login o al inicio
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificacion = NotificationCompat.Builder(this, CANAL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(titulo)
            .setContentText(cuerpo)
            .setStyle(NotificationCompat.BigTextStyle().bigText(cuerpo))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "Sin permiso de notificaciones, no se muestra.")
            return
        }

        NotificationManagerCompat.from(this)
            .notify(System.currentTimeMillis().toInt(), notificacion)
    }
}