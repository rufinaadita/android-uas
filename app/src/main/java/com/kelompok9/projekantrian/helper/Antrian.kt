package com.kelompok9.projekantrian.helper

import java.io.Serializable

data class Antrian(
    var id: String? = "",
    var usia: String? = "",
    var alamat: String? = "",
    var nohp: String? = "",
    var gender: String? = ""
) : Serializable