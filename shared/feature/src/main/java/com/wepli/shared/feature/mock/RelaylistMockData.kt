package com.wepli.shared.feature.mock

import android.annotation.SuppressLint
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData
import com.wepli.uimodel.music.toDomain
import model.relaylist.Relaylist
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy.MM.dd")

val relaylistMockData = listOf(
    Relaylist(
        id = 0,
        title = "그 시절, 아무 걱정 없었던 그때",
        description = "어린 시절이 생각나는 노래 모음",
        coverImgUrl = "https://img.29cm.co.kr/cms/202411/11efa2f103b3aea1afbcaff1c242f37c.jpg?width=2000&q=75",
        bSideTrack = songMockData.map { it.toDomain() },
        songCount = 10,
        voteCount = 100,
        endDate = DateTime.parse("2025.03.12", formatter),
        createdAt = DateTime.parse("2025.01.01", formatter),
        artwork = Relaylist.Artwork(backgroundColor = 0xFF9B9BA5),
    ),
    Relaylist(
        id = 1,
        title = "감성 한 잔, 카페 BGM",
        description = "트는 순간 바로 인생샷",
        coverImgUrl = "https://img.29cm.co.kr/cms/202411/11efa330c272ddb9afbc0dbc604d203e.jpg?q=75",
        bSideTrack = songMockData.map { it.toDomain() },
        songCount = 10,
        voteCount = 100,
        endDate = DateTime.parse("2025.03.12", formatter),
        createdAt = DateTime.parse("2025.01.01", formatter),
        artwork = Relaylist.Artwork(backgroundColor = 0xFFA1A1AA),
    ),
    Relaylist(
        id = 2,
        title = "첫 눈 오던 날",
        description = "추운 날씨에 어울리는 한 겨울 리메이크 곡",
        coverImgUrl = "https://img.29cm.co.kr/cms/202411/11efa32fd888d0eb86c37fb1ce8c821c.JPG?q=75",
        bSideTrack = songMockData.map { it.toDomain() },
        songCount = 10,
        voteCount = 100,
        endDate = DateTime.parse("2025.03.12", formatter),
        createdAt = DateTime.parse("2025.01.01", formatter),
        artwork = Relaylist.Artwork(backgroundColor = 0xFF595758),
    ),
    Relaylist(
        id = 3,
        title = "썸녀한테 추천해줄 감성 싱잉랩",
        description = "카톡하다 대화 소재 떨어 졌을때",
        coverImgUrl = "https://img.29cm.co.kr/cms/202411/11ef9ff687cc9f379876cf8486967618.jpg?q=75",
        bSideTrack = songMockData.map { it.toDomain() },
        songCount = 10,
        voteCount = 100,
        endDate = DateTime.parse("2025.03.12", formatter),
        createdAt = DateTime.parse("2025.01.01", formatter),
        artwork = Relaylist.Artwork(backgroundColor = 0xFFB27F53),
    ),
)

@SuppressLint("SimpleDateFormat")
val relaylistUiMockData = relaylistMockData.map { RelaylistUiData.fromDomain(it) }