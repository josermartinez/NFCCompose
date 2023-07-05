package com.example.nfccompose.di

import android.content.Context
import android.nfc.NfcAdapter
import com.example.nfccompose.data.DeviceRepositoryImpl
import com.example.nfccompose.domain.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideNfcAdapter(@ApplicationContext context: Context): NfcAdapter? {
        return NfcAdapter.getDefaultAdapter(context)
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(nfcAdapter: NfcAdapter?): DeviceRepository {
        return DeviceRepositoryImpl(nfcAdapter = nfcAdapter)
    }
}