package com.turdroid.library.network.environment

enum class Environmental(val url: String) {
    DEV("http://192.168.1.168:9999/"),
    TEST("http://39.97.168.80:9999/"),
    LIVE("http://k3s.ksjade.com:31000/");

}