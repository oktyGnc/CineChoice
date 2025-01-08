package com.oktaygenc.cinechoice.data.model.entitiy

data class Movie(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val price: Int = 0,
    val category: String = "",
    val rating: Double = 0.0,
    val year: Int = 0,
    val director: String = "",
    val description: String = ""
) {
    constructor() : this(0, "", "", 0, "", 0.0, 0, "", "")
}
