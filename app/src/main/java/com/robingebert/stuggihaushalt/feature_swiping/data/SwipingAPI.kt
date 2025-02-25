package com.robingebert.stuggihaushalt.feature_swiping.data

import com.robingebert.stuggihaushalt.data.KtorClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.bodyAsText
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

    suspend fun vote(link: String): Boolean {
        return false
    }
}