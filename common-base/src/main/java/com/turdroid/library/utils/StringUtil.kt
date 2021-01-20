package com.turdroid.library.utils;


import java.util.regex.Matcher
import java.util.regex.Pattern

object StringUtil {

    fun limitString(password: String?): Boolean {
        val string = "^[A-Za-z0-9,.，。…+-/*！!()\u4e00-\u9fa5]+$"
        val mPattern: Pattern = Pattern.compile(string)
        val matcher: Matcher = mPattern.matcher(password)
        return matcher.matches()
    }
}