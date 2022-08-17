package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.kodein.di.*
import kotlin.coroutines.CoroutineContext

val di = DI {

    //bind<CoroutineContext>() with provider { ApplicationDispatcher }

    bind<CoroutineContext>() with provider { Dispatchers.Main }

    val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}