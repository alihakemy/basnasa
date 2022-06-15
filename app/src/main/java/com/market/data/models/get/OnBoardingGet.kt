package com.market.data.models.get


data class OnBoardingGet(
    val `data`: List<Data>,
    val status: String
)

data class Data(
    val content: String,
    val id: Int,
    val image: String,
    val title: String
)