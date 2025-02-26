package com.robingebert.stuggihaushalt.feature_swiping.repository

import com.fleeksoft.ksoup.Ksoup
import com.robingebert.stuggihaushalt.common.base.BaseRepository
import com.robingebert.stuggihaushalt.data.KtorClient
import com.robingebert.stuggihaushalt.feature_swiping.data.SwipingAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class SwipingRepository(private val client: KtorClient) : BaseRepository() {

    private val swipingAPI = SwipingAPI(client)

    suspend fun getNewViews(): Result<List<Proposal>> = safeCall {
        val response: List<JsonElement> = Json.decodeFromString(swipingAPI.getNewViews())
        val rawResponse = response[1].jsonObject["data"].toString()
        val cleanedResponse = rawResponse.replace("\\n", "").replace("\\\"", "\"")
        val document = Ksoup.parse(cleanedResponse)
        var list = mutableListOf<Proposal>()
        document.getElementsByClass("views-row").forEach {
            val rateButtons = it.getElementsByClass("rate-button").map {
                extractToken(it.attr("href"))
            }
            list.add(
                Proposal(
                    title = it.getElementsByClass("node__title node-title")[0].text(),
                    content = it.getElementsByClass("field-body")[0].firstElementChild()
                        ?.firstElementChild()?.text() ?: "",
                    author = it.getElementsByClass("username")[0].text(),
                    county = it.getElementsByClass("vocabulary-ort")[0].text(),
                    category = it.getElementsByClass("shs-term-selected")[0].text(),
                    effect = it.getElementsByClass("field-tax-wirkung")[0].firstElementChild()
                        ?.firstElementChild()?.text() ?: "",
                    optionBad = rateButtons[0],
                    optionGood = rateButtons[1],
                    optionWhatever = rateButtons[2],
                )
            )

        }
        list
    }

    suspend fun rateProposal(proposal: Proposal, action: ProposalAction): Result<Unit> = safeCall {
        when (action) {
            ProposalAction.GOOD -> swipingAPI.vote(proposal.optionGood)
            ProposalAction.BAD -> swipingAPI.vote(proposal.optionBad)
            ProposalAction.WHATEVER -> swipingAPI.vote(proposal.optionWhatever)
        }
    }

    fun extractToken(url: String): String {
        return url.split("rate=")[1]
    }
}