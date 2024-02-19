package com.example.proyectopersonalizado.objets_models

import com.example.proyectopersonalizado.models.Bar

object Repository {
    var listBars : List<Bar> = listOf(
        Bar(
            "El Paso", "Cazorla", "Jaén", "123456789", "http://cazorla.es/turismo/wp-content/uploads/2018/01/ELPASO.png"
        ),
        Bar(
            "La Toba", "Cazorla", "Jaén", "123456789", "https://www.turismoencazorla.com/dondecomer/latoba/imagen/big/terraza.jpg"
        ),
        Bar(
            "Casa Tino", "Cazorla", "Jaén", "123456789", "https://guiadecazorlayubeda.com/wp-content/uploads/2012/08/casa-tino.jpg"
        ),
        Bar(
            "La Cantina", "Cazorla", "Jaén", "123456789", "https://guiadecazorlayubeda.com/wp-content/uploads/WhatsApp-Image-2021-03-26-at-17.13.43-1-e1616778519348.jpeg"
        ),
        Bar(
            "La Yedra", "Cazorla", "Jaén", "123456789", "https://lh5.googleusercontent.com/p/AF1QipO0Sl7ojkbfsy1DAmrZ1U6NP5tf81XTUYuMNqAq"
        ),
        Bar(
            "El Paso", "Cazorla", "Jaén", "123456789", "http://cazorla.es/turismo/wp-content/uploads/2018/01/ELPASO.png"
        ),
        Bar(
            "La Toba", "Cazorla", "Jaén", "123456789", "https://www.turismoencazorla.com/dondecomer/latoba/imagen/big/terraza.jpg"
        ),
        Bar(
            "Casa Tino", "Cazorla", "Jaén", "123456789", "https://guiadecazorlayubeda.com/wp-content/uploads/2012/08/casa-tino.jpg"
        ),
        Bar(
            "La Cantina", "Cazorla", "Jaén", "123456789", "https://guiadecazorlayubeda.com/wp-content/uploads/WhatsApp-Image-2021-03-26-at-17.13.43-1-e1616778519348.jpeg"
        ),
        Bar(
            "La Yedra", "Cazorla", "Jaén", "123456789", "https://lh5.googleusercontent.com/p/AF1QipO0Sl7ojkbfsy1DAmrZ1U6NP5tf81XTUYuMNqAq"
        )
    )
    fun addHotel(bar: Bar) {
        val updatedList = listBars.toMutableList()
        updatedList.add(bar)
        listBars = updatedList.toList()
    }




}