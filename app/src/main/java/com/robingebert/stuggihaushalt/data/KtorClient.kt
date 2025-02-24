package com.robingebert.stuggihaushalt.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters

class KtorClient {
    private val cookieStorage = AcceptAllCookiesStorage()

    val client = HttpClient(CIO) {
        install(HttpCookies) {
            storage = cookieStorage
        }
    }

    suspend fun login(username: String, password: String, onLogin: (Result<String>) -> Unit) {
        val response = client.submitForm(
            url = "https://www.buergerhaushalt-stuttgart.de/seite/7186?destination=node/7186",
            formParameters = parameters {
                append("form_id", "user_login_block")
                append("name", username)
                append("pass", password)
            }
        )

        // Prüfen, ob der Login erfolgreich war
        if (response.status == HttpStatusCode.OK || response.status == HttpStatusCode.Found) {
            onLogin(Result.success("Login erfolgreich"))
        } else {
            onLogin(Result.failure(Exception("Fehler: ${response.bodyAsText()}")))
        }
    }

}