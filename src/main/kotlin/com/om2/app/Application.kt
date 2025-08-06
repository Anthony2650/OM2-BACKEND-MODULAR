package com.example.com.om2.app

import com.example.com.om2.app.plugins.configureHTTP
import com.om2.app.plugins.configureRouting
import com.example.com.om2.app.plugins.configureSecurity
import com.example.com.om2.app.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRouting()
}
