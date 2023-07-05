package com.example.nfccompose.domain

import android.nfc.Tag
import com.example.nfccompose.domain.models.MifareClassicData
import com.example.nfccompose.domain.models.NfcData

interface DeviceRepository {

    fun isNFCSupported(): Boolean

    fun isNFCEnabled(): Boolean

    fun getNfcADataFromTag(tag: Tag): NfcData?

    fun getMifareClassicDataFromTag(tag: Tag): MifareClassicData?
}