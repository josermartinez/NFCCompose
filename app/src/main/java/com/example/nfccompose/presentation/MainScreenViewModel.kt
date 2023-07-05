package com.example.nfccompose.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfccompose.domain.DeviceRepository
import com.example.nfccompose.utils.toHexString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val deviceRepository: DeviceRepository) :
    ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    init {
        state = state.copy(
            isNFCSupported = deviceRepository.isNFCSupported(),
            isNFCEnabled = deviceRepository.isNFCEnabled(),
        )
    }

    fun updateIntent(intent: Intent?) {
        state = state.copy(loading = true)
        val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
        } else {
            intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG) as? Tag
        }

        viewModelScope.launch {
            processTag(tag = tag)
            state = state.copy(loading = false)
        }

    }

    fun onActivityForResultFinished(code: Int) {
        state = state.copy(isNFCEnabled = deviceRepository.isNFCEnabled())
    }

    private fun processTag(tag: Tag?) {
        tag?.let { techTag ->
            state =
                state.copy(
                    errorMessage = null,
                    tagSupportedTech = tag.techList?.joinToString(", ") { it.removePrefix("android.nfc.tech.") }
                        .orEmpty(),
                    tagId = techTag.id.toHexString(),
                )

            try {
                deviceRepository.getNfcADataFromTag(tag)?.let {
                    state = state.copy(tagAtqa = it.atqa, tagSak = it.sak)
                }
                deviceRepository.getMifareClassicDataFromTag(tag)?.let {
                    state =
                        state.copy(
                            tagMemoryInformation = "${it.memorySize} bytes: ${it.sectorCount} sectors of ${it.blockCount} blocks",
                            mifareData = it.data,
                        )
                }
            } catch (e: Exception) {
                state = state.copy(loading = false, errorMessage = e.message)
            }
        }
    }
}