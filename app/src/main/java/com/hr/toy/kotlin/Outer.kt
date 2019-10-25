package com.hr.toy.kotlin

import android.util.Log

class Outer {

    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }

    inner class Inner {
        fun foo() = bar
        fun innerTest() {
            var o = this@Outer
            Log.e(HelloWorld.TAG, "内部类可以引用外部类的成员，例如：${o.bar}")
        }
    }

    fun setInterfaceTest(testInterface: TestInterface) {
        testInterface.test()
    }

}