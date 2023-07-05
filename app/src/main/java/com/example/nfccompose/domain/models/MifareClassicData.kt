package com.example.nfccompose.domain.models

data class MifareClassicData(
    val memorySize: Int,
    val sectorCount: Int,
    val blockCount: Int,
    val data: String,
)
