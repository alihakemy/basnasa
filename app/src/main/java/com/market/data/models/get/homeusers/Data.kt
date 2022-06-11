package com.market.data.models.get.homeusers

data class Data(
    val banner: List<Banner>,
    val categories: List<Category>,
    val merchants: List<Merchant>,
    val products: List<Product>,
    val slider: List<Slider>
)