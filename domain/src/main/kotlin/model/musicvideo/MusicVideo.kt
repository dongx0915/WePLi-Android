package model.musicvideo

import common.DomainModel

data class MusicVideo(
    val id: String,
    val title: String,
    val channelTitle: String,
    val thumbnail: String,
) : DomainModel