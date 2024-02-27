package com.example.proyectopersonalizado.dao

import com.example.proyectopersonalizado.interfaces.InterfaceDao
import com.example.proyectopersonalizado.models.Bar
import com.example.proyectopersonalizado.objets_models.Repository

class DaoBares private constructor() : InterfaceDao{
    companion object{
        val miDao : DaoBares by lazy {
            DaoBares()
        }
    }

    override fun getDataBares(): List<Bar> = Repository.listBars
}