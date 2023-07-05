package com.example.nfccompose.utils

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

fun Boolean?.orFalse() = this ?: false