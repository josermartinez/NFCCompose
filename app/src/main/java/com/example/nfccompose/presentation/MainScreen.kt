package com.example.nfccompose.presentation

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nfccompose.presentation.components.ListInformationTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onActivityForResultFinished: (Int) -> Unit
) {
    val nfcLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            onActivityForResultFinished(it.resultCode)
        }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
            )
        },
    ) { paddingValues ->
        when {
            state.loading -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Do not put away the nfc tag",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            state.isNFCSupported.not() -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "NFC is not supported on this device",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            state.isNFCEnabled.not() -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "NFC is disabled. Please enable NFC",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(onClick = { nfcLauncher.launch(Intent(Settings.ACTION_NFC_SETTINGS)) }) {
                        Text(text = "Enable NFC")
                    }
                }
            }

            state.errorMessage.isNullOrEmpty().not() -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.errorMessage ?: "Error",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }

            state.tagId.isEmpty() -> {
                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Approach an NFC tag",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.tagId.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.Key,
                                title = "Serial Number",
                                subtitle = state.tagId
                            )
                            Divider()
                        }
                    }
                    if (state.tagSupportedTech.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.Info,
                                title = "Technologies available",
                                subtitle = state.tagSupportedTech,
                            )
                            Divider()
                        }
                    }

                    if (state.tagAtqa.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.Abc,
                                title = "ATQA",
                                subtitle = state.tagAtqa,
                            )
                            Divider()
                        }
                    }

                    if (state.tagSak.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.Abc,
                                title = "SAK",
                                subtitle = state.tagSak,
                            )
                            Divider()
                        }
                    }

                    if (state.tagMemoryInformation.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.Memory,
                                title = "Memory Information",
                                subtitle = state.tagMemoryInformation,
                            )
                            Divider()
                        }
                    }

                    if (state.mifareData.isNotEmpty()) {
                        item {
                            ListInformationTile(
                                modifier = Modifier.padding(vertical = 8.dp),
                                leadingIcon = Icons.Default.DataObject,
                                title = "Data",
                                subtitle = state.mifareData,
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    }
}