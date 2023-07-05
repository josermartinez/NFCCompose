package com.example.nfccompose

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcA
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.nfccompose.presentation.MainScreen
import com.example.nfccompose.presentation.MainScreenViewModel
import com.example.nfccompose.ui.theme.NFCComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainScreenViewModel>()

    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFilter: Array<IntentFilter>
    private val techListArray = arrayOf(
        arrayOf<String>(
            NfcA::class.java.name,
            MifareClassic::class.java.name,
            NdefFormatable::class.java.name
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        intentFilter = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))

        viewModel.updateIntent(getIntent())

        setContent {
            NFCComposeTheme {
                val state = viewModel.state
                MainScreen(
                    state = state,
                    onActivityForResultFinished = viewModel::onActivityForResultFinished
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        NfcAdapter.getDefaultAdapter(this)
            .enableForegroundDispatch(this, pendingIntent, intentFilter, techListArray)
    }

    override fun onPause() {
        super.onPause()
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        viewModel.updateIntent(intent)
    }
}