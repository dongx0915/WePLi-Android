package com.wepli.data.youtube.repository

import com.wepli.core.kotlin.flow.FlowResult
import model.musicvideo.MusicVideo

interface YoutubeRepository {

    fun searchMusicVideo(searchQuery: String): FlowResult<MusicVideo>
}