package com.robingebert.stuggihaushalt.feature_swiping.repository

import com.fleeksoft.ksoup.Ksoup
import com.robingebert.stuggihaushalt.common.base.BaseRepository
import com.robingebert.stuggihaushalt.data.KtorClient
import com.robingebert.stuggihaushalt.feature_swiping.data.SwipingAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class SwipingRepository(private val client: KtorClient): BaseRepository() {

    private val swipingAPI = SwipingAPI(client)

    suspend fun getNewViews(): Result<List<Proposal>> = safeCall {
        val response:List<JsonElement> = Json.decodeFromString(swipingAPI.getNewViews())
        val document = Ksoup.parse(response[2].jsonObject["data"].toString())
        //document.childNodes()[2].
        emptyList()
    }
}