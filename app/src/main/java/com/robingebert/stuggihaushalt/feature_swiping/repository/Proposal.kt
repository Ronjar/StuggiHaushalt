package com.robingebert.stuggihaushalt.feature_swiping.repository

data class Proposal(
    val title: String,
    val content: String,
    val author: String,
    val county: String,
    val tag: String,
    val optionGood: String,
    val optionBad: String,
    val optionWhatever: String,
)
