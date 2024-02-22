package com.example.proyectopersonalizado.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DBUEntity {
        return Room.databaseBuilder(
            context,
            DBUEntity::class.java,
            "MY_DATABASE_USUARIO"
        ).build()
    }
}