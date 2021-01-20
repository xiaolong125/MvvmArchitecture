package com.turdroid.library.utils

import java.math.BigDecimal

object MoneyUtils {

    fun formatMoney(money:Double):Double{
        val b = BigDecimal(money)
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    }
}