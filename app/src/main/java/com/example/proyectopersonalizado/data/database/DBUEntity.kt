package com.example.proyectopersonalizado.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyectopersonalizado.data.dao.UEntityDao
import com.example.proyectopersonalizado.data.entities.UsuarioEntity

@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = false)
abstract class DBUEntity : RoomDatabase() {
    abstract fun usuarioEntityDao(): UEntityDao

    companion object {
        @Volatile
        private var INSTANCE: DBUEntity? = null

        fun getDatabase(context: Context): DBUEntity {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBUEntity::class.java,
                    "MY_DATABASE"
                )
                    .addCallback(object : RoomDatabase.Callback() {

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Log.d("Room", "Base de datos abierta")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}