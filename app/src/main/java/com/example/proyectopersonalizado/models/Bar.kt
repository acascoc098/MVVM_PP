package com.example.proyectopersonalizado.models

class Bar (
    var name: String,
    var city: String,
    var province: String,
    var phone: String,
    var image: String
) {
    override fun toString(): String {
        return "Bar(name='$name', city='$city', province='$province', phone='$phone', image='$image')"
    }
}