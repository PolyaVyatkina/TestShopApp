package com.molkos.testshopapp.data.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlin.text.Charsets

object KtorClient {

    val client: HttpClient = HttpClient(CIO) {
        install(Logging)
        expectSuccess = false
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        Charsets {
            register(Charsets.UTF_8)
        }
    }
}