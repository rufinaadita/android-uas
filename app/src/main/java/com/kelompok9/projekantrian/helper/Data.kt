package com.kelompok9.projekantrian.helper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val antrians: Antrian,
    val nomor_antrian: String,
    val status: String,
    val tgl_antri: String,
    val users: User
) : Parcelable