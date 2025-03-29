package model.album

import common.DomainModel
import model.music.Song

data class Album(
    val id: String,
    val href: String,
    val name: String,
    val description: String,
    val coverImg: String,
    val albumUrl: String,
    val isSingle: Boolean,
    val artistId: String,
    val artistName: String,
    val releaseDate: String,
    val trackCount: Int,
    val tracks: List<Song>,
    val genres: List<String>,
) : DomainModel