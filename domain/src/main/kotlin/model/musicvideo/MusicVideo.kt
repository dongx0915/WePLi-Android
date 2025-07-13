package model.musicvideo

import common.DomainModel

data class MusicVideo(
    private val id: String,
    private val title: String,
    private val playtime: Float,
    private val thumbnail: String,
) : DomainModel