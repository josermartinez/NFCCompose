package com.example.nfccompose.presentation

data class MainScreenState(
    val loading: Boolean = false,
    val isNFCSupported: Boolean = false,
    val isNFCEnabled: Boolean = false,
    val tagId: String = "",
    val tagSupportedTech: String = "",
    val tagAtqa: String = "",
    val tagSak: String = "",
    val tagMemoryInformation: String = "",
    val mifareData: String = "",
    val errorMessage: String? = null,
)
