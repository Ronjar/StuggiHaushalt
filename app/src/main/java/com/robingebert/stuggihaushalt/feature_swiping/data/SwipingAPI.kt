package com.robingebert.stuggihaushalt.feature_swiping.data

import com.robingebert.stuggihaushalt.data.KtorClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareGet
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters

class SwipingAPI(private val client: KtorClient) {
    suspend fun getNewViews(): String {
        val request = client.client.submitForm(
            url = "https://www.buergerhaushalt-stuttgart.de/views/ajax",
            formParameters = parameters {
                append("page", "1")
                append("view_name", "zubewerten_zufall")
                append("view_display_id", "page_7")
                append("view_path", "vorschlaege/filtern")
            }
        )
        return request.bodyAsText()
    }

    suspend fun vote(token: String): Boolean {
        val response = client.client.prepareGet(
            urlString = "https://www.buergerhaushalt-stuttgart.de/vorschlaege/filtern",
            block = {
                parameter("rate", token)
            }).execute()
        return response.status == HttpStatusCode.OK

    }
}