package com.example.proyectopersonalizado.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity (
    @PrimaryKey(autoGenerate = true) val id: Int=1,
    @ColumnInfo(name="usuario") val usuario:String,
    @ColumnInfo(name="contrasena") val contrasena:String
)