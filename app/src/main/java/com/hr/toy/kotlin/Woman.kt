package com.hr.toy.kotlin

class Woman(city: String) : Person(city) {

    override fun marry() {
        wife = Woman("长安")
    }
}