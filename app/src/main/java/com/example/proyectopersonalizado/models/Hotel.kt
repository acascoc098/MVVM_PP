package com.example.proyectopersonalizado.models

class Hotel (
    var name: String,
    var city: String,
    var province: String,
    var phone: String,
    var image: String
) {
    override fun toString(): String {
        return "Hotel(name='$name', city='$city', province='$province', phone='$phone', image='$image')"
    }
}