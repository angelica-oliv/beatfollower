package com.angelicao.beatfollower

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.health.services.client.HealthServices
import androidx.health.services.client.HealthServicesClient
import com.angelicao.beatfollower.data.HealthRepositoryApi
import com.angelicao.beatfollower.data.HealthRepositoryImpl
import com.angelicao.beatfollower.domain.HealthServicesManagerApi
import com.angelicao.beatfollower.domain.HealthServicesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Hilt module that provides singleton (application-scoped) objects.
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideHealthServicesClient(@ApplicationContext context: Context): HealthServicesClient =
        HealthServices.getClient(context)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MainActivityModule {
    @Binds
    abstract fun bindHealthServicesManager(healthServicesManagerImpl: HealthServicesManagerImpl): HealthServicesManagerApi

    @Binds
    abstract fun bindHealthRepository(healthRepositoryImpl: HealthRepositoryImpl): HealthRepositoryApi
}

const val PREFERENCES_FILENAME = "passive_data_prefs"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_FILENAME)