package model.album

import common.DomainModel

data class Album(
    val id: String,
    val href: String,
    val name: String,
    val artistName: String,
    val genres: List<String>,
    val releaseDate: String,
    val coverImg: String,
    val trackCount: Int,
    val description: String,
) : DomainModel