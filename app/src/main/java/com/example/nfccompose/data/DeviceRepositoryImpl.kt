package com.example.nfccompose.data

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import com.example.nfccompose.domain.DeviceRepository
import com.example.nfccompose.domain.models.MifareClassicData
import com.example.nfccompose.domain.models.NfcData
import com.example.nfccompose.utils.orFalse
import com.example.nfccompose.utils.toHexString
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(private val nfcAdapter: NfcAdapter?) :
    DeviceRepository {

    override fun isNFCSupported(): Boolean {
        return nfcAdapter != null
    }

    override fun isNFCEnabled(): Boolean {
        return nfcAdapter?.isEnabled.orFalse()
    }

    override fun getNfcADataFromTag(tag: Tag): NfcData? {
        return if (tag.techList.contains(NfcA::class.java.name)) {
            NfcA.get(tag).use { nfcA ->
                nfcA.connect()
                val tagAtqa = nfcA.atqa.toHexString()
                val tagSak = nfcA.sak.toString()
                nfcA.close()
                NfcData(atqa = tagAtqa, sak = tagSak)
            }
        } else null
    }

    override fun getMifareClassicDataFromTag(tag: Tag): MifareClassicData? {
        return if (tag.techList.contains(MifareClassic::class.java.name)) {
            MifareClassic.get(tag).use { mifare ->
                var data = ""
                mifare.connect()
                for (index in 0 until mifare.sectorCount) {
                    if (mifare.isConnected
                        && (mifare.authenticateSectorWithKeyA(
                            index,
                            MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY
                        ) ||
                                mifare.authenticateSectorWithKeyA(index, MifareClassic.KEY_DEFAULT)
                                ||
                                mifare.authenticateSectorWithKeyA(
                                    index,
                                    MifareClassic.KEY_NFC_FORUM
                                ))
                    ) {
                        val block = mifare.sectorToBlock(index)
                        data += mifare.readBlock(block).toHexString()
                    }
                }
                mifare.close()

                MifareClassicData(
                    memorySize = mifare.size,
                    sectorCount = mifare.sectorCount,
                    blockCount = mifare.blockCount,
                    data = data
                )
            }
        } else null
    }
}