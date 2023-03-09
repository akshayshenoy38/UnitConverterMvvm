package com.example.unitconvertermvvm.di

import android.app.Application
import androidx.room.Room
import com.example.unitconvertermvvm.data.ConverterDatabase
import com.example.unitconvertermvvm.data.ConverterRepository
import com.example.unitconvertermvvm.data.ConverterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideConverterDatabase(app:Application):ConverterDatabase =
        Room.databaseBuilder(
            app,
            ConverterDatabase::class.java,
            "converter_data_database"
        ).build()

    @Singleton
    @Provides
    fun provideConverterRepository(db:ConverterDatabase) :ConverterRepository {
        return ConverterRepositoryImpl(db.converterDAO)
    }
}