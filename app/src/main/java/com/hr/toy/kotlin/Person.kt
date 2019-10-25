package com.hr.toy.kotlin

import android.util.Log

open class Person(city : String){

    val name: String = "Ryan"
        get() = field.toLowerCase()


    var city: String = "SHENZHENG"
        set(value) {
            field = value.toUpperCase()
        }
        get() = field

    init {
        Log.e("Kotlin", "Person invoke init")
        this.city = city
    }

    private lateinit var wife: Woman

    fun marry() {
        wife = Woman("西安")
    }

    fun wife(): Woman {
        return wife
    }

}