package com.example.pry_vital_dent_movil

import com.example.pry_vital_dent_movil.Modelo.DataMultimedia
import com.example.pry_vital_dent_movil.Modelo.clsReproductorModelo
import com.example.pry_vital_dent_movil.Presentador.clsMultimediaPresentador
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// Pruebas unitarias de la lógica del módulo multimedia (Fase 3.2 / Fase 4)
// Ejecutar: clic derecho sobre este archivo > Run 'MultimediaPruebasUnitarias'
class MultimediaPruebasUnitarias {

    @Test
    fun formatearTiempo_minutosYSegundos() {
        assertEquals("0:00", clsReproductorModelo.FormatearTiempo(0))
        assertEquals("0:09", clsReproductorModelo.FormatearTiempo(9_999))
        assertEquals("1:15", clsReproductorModelo.FormatearTiempo(75_000))
    }

    @Test
    fun formatearTiempo_conHoras() {
        assertEquals("1:02:05", clsReproductorModelo.FormatearTiempo(3_725_000))
    }

    @Test
    fun formatearTiempo_negativoEsCero() {
        assertEquals("0:00", clsReproductorModelo.FormatearTiempo(-500))
    }

    @Test
    fun calcularSalto_noSeSaleDelArchivo() {
        assertEquals(0L, clsReproductorModelo.CalcularSalto(4_000, 48_000, -10_000))
        assertEquals(48_000L, clsReproductorModelo.CalcularSalto(45_000, 48_000, 10_000))
        assertEquals(25_000L, clsReproductorModelo.CalcularSalto(15_000, 48_000, 10_000))
    }

    @Test
    fun urlValida_soloHttpYHttps() {
        assertTrue(clsReproductorModelo.EsUrlValida("https://victor.4buthh.com/APIS_proyecto/multimedia/a.mp3"))
        assertTrue(clsReproductorModelo.EsUrlValida("http://192.168.137.1/APIS_proyecto/multimedia/a.mp4"))
        assertFalse(clsReproductorModelo.EsUrlValida(""))
        assertFalse(clsReproductorModelo.EsUrlValida(null))
        assertFalse(clsReproductorModelo.EsUrlValida("file:///sdcard/a.mp4"))
    }

    @Test
    fun pestanas_seConviertenEnTipo() {
        assertNull(clsMultimediaPresentador.TipoDePestana(0))
        assertEquals(DataMultimedia.TIPO_VIDEO, clsMultimediaPresentador.TipoDePestana(1))
        assertEquals(DataMultimedia.TIPO_AUDIO, clsMultimediaPresentador.TipoDePestana(2))
    }

    @Test
    fun dataMultimedia_esVideo() {
        assertTrue(DataMultimedia(tipo = "video").esVideo)
        assertFalse(DataMultimedia(tipo = "audio").esVideo)
    }
}
