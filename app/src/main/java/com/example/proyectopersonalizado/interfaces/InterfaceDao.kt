package com.example.proyectopersonalizado.interfaces

import com.example.proyectopersonalizado.models.Bar

interface InterfaceDao {
    fun getDataBares(): List<Bar>
}