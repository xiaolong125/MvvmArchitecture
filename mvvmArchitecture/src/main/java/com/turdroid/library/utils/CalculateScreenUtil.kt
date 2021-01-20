package com.turdroid.library.utils

import kotlin.math.pow
import kotlin.math.sqrt

object CalculateScreenUtil {

    @JvmStatic
    fun main(args: Array<String>) {
        val result = calculate(375.0,667.0)
        println("计算结果:$result")
    }

    private fun calculate(width:Double, height:Double) :Double{
        return sqrt(width.pow(2.0) + height.pow(2.0)) /72.0
    }
}