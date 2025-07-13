package model.musicvideo

import common.DomainModel

data class MusicVideo(
    val id: String,
    val title: String,
    val playtime: Float,
    val thumbnail: String,
) : DomainModel