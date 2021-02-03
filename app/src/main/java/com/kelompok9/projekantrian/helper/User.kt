package com.kelompok9.projekantrian.helper

import java.io.Serializable

data class User(
    var id: String? = "",
    var name: String? = "",
    var email: String? = "",
    var password: String? = ""
) : Serializable