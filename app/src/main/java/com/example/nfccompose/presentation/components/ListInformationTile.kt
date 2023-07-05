package com.example.nfccompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfccompose.ui.theme.NFCComposeTheme

@Composable
fun ListInformationTile(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .size(30.dp)
        )
        Column(modifier = Modifier.weight(5f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
@Preview
fun ListInformationTilePreview() {
    NFCComposeTheme() {
        ListInformationTile(
            leadingIcon = Icons.Default.Memory,
            title = "Technologies available",
            subtitle = "NFC"
        )
    }
}