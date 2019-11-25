package com.hr.toy.kotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.hr.toy.R

class HelloWorld : Activity() {

    companion object {
        const val TAG = "Kotlin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_btn_and_textview)
        var tv = findViewById<TextView>(R.id.textview)

        val person = Person("xiamen")
        person.city = "GuangZhou"
        tv.text = "name = ${person.name} , city = ${person.city}, changeCity = ${changeCity(person.city)}, age = ${addAge(10, 9)}"
        vars(1, 2, 3, 4, 5, 6)
        Log.e(TAG, "addStr = ${addStr("aaaaaa", "bbbbbb")}")
        isInstanceOf("test")
        rangeTest()
        whenTest(6)
        forTest()
        labelTest()
        initTest()
        classTest()
        extendFunTest()
    }

    fun printDivider() {
        Log.e(TAG, "-------")
    }

    fun changeCity(city: String): String {
        return city.reversed()
    }

    fun addAge(a: Int, b: Int) = a + b

    fun vars(vararg v: Int) {
        for (vt in v) {
            Log.e(TAG, vt.toString())
        }
    }

    fun addStr(a: String, b: String): String {
        return a + b
    }

    fun isInstanceOf(a: Any) {
        if (a is String) Log.e(TAG, "a is String")
        if (a is Int) Log.e(TAG, "a is Int")
        if (a is Float) Log.e(TAG, "a is Float")
    }

    fun rangeTest() {
        printDivider()
        for (i in 1..5) Log.e(TAG, i.toString())
        printDivider()
        for (i in 5 downTo 1 step 2) Log.e(TAG, i.toString())
        printDivider()
        for (i in 1 until 8 step 3) Log.e(TAG, i.toString())
    }

    fun whenTest(x: Int) {
        printDivider()
        when (x) {
            1 -> Log.e(TAG, "x == 1")
            2 -> Log.e(TAG, "x == 2")
            3 -> Log.e(TAG, "x == 3")
            in 4..6 -> {
                Log.e(TAG, "x >= 4 and x <= 6")
            }
            else -> Log.e(TAG, "x not found")
        }

    }

    private fun forTest() {
        printDivider()
        val array = arrayOf("a", "b", "c", "d")
        for (index in array.indices) {
            Log.e(TAG, array[index])
        }
        printDivider()
        for ((i, value) in array.withIndex()) {
            Log.e(TAG, "index = $i, value = $value")
        }
    }

    private fun labelTest() {
        printDivider()
        loop@ for (i in 1..10) {
            for (j in 1..10) {
                Log.e(TAG, "j = $j")
                if (j == 3) break@loop
            }
        }

        printDivider()
        label@ for (i in 1..10) {
            for (j in 1..10) {
                Log.e(TAG, "j = $j")
                if (j == 3) continue@label
            }
        }
    }

    private fun initTest() {
        printDivider()
        var runoob = Runoob("百度", 100)
        runoob.test()
    }

    private fun classTest() {
        printDivider()
        val demo = Outer.Nested().foo()
        Log.e(TAG, "demo = $demo")

        printDivider()
        Outer().Inner().innerTest()
        Outer().setInterfaceTest(object : TestInterface {
            override fun test() {
                Log.e(TAG, "匿名内部类测试")
            }
        })
    }

    fun Person.sayHello() {
        Log.e(TAG, "hello !!!!")
    }

    private fun extendFunTest() {
        printDivider()
        var p = Person("HK")
        p.sayHello()
    }

}