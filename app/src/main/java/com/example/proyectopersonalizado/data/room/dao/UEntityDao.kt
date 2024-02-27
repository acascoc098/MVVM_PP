package com.example.proyectopersonalizado.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectopersonalizado.data.room.entities.UsuarioEntity
import com.example.proyectopersonalizado.objets_models.Usuario

@Dao
interface UEntityDao{
    @Query("SELECT * FROM usuarios")
    fun getAll(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND contrasena = :contrasena")
    fun getUsuarioByUsuarioAndPassword(usuario: String, contrasena: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario ")
    fun getUsuarioByUsuario(usuario: String): UsuarioEntity

    @Query("DELETE FROM usuarios")
    fun borrarUsuarioPorId()
}