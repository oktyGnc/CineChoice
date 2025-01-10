package com.oktaygenc.cinechoice.data.mapper

import com.oktaygenc.cinechoice.data.model.entitiy.CardItem
import com.oktaygenc.cinechoice.domain.model.CartItemModel

fun CardItem?.toModel() = CartItemModel(
    cartIdList = emptyList(),
    name = this?.name.orEmpty(),
    image = this?.image.orEmpty(),
    price = this?.price ?: 0,
    category = this?.category.orEmpty(),
    rating = this?.rating ?: 0.0,
    year = this?.year ?: 0,
    director = this?.director.orEmpty(),
    description = this?.description.orEmpty(),
    orderAmount = this?.orderAmount ?: 0,
    userName = this?.userName.orEmpty()
)