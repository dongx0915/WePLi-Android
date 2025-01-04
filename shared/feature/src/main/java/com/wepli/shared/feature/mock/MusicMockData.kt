package com.wepli.shared.feature.mock

import com.wepli.uimodel.music.ChartMusicUiData
import com.wepli.uimodel.music.SongUiData


val musicMockData = listOf(
    ChartMusicUiData(1, "HAPPY", "DAY6 (데이식스)", "Fourever", "https://cdnimg.melon.co.kr/cm2/album/images/114/44/397/11444397_20240318115810_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(2, "Welcome to the Show", "DAY6 (데이식스)", "Fourever", "https://cdnimg.melon.co.kr/cm2/album/images/114/44/397/11444397_20240318115810_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(3, "Supernova", "aespa", "Armageddon - The 1st Album", "https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(4, "한 페이지가 될 수 있게", "DAY6 (데이식스)", "The Book of Us : Gravity", "https://cdnimg.melon.co.kr/cm/album/images/103/07/346/10307346_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(5, "녹아내려요", "DAY6 (데이식스)", "Band Aid", "https://cdnimg.melon.co.kr/cm2/album/images/115/80/616/11580616_20240830171855_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(6, "클락션 (Klaxon)", "(여자)아이들", "I SWAY", "https://cdnimg.melon.co.kr/cm2/album/images/115/33/515/11533515_20240708112323_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(7, "How Sweet", "NewJeans", "How Sweet", "https://cdnimg.melon.co.kr/cm2/album/images/114/75/749/11475749_20240524105642_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(8, "고민중독", "QWER", "1st Mini Album 'MANITO'", "https://cdnimg.melon.co.kr/cm2/album/images/114/54/681/11454681_20240328174504_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(9, "예뻤어", "DAY6 (데이식스)", "Every DAY6 February", "https://cdnimg.melon.co.kr/cm/album/images/100/36/099/10036099_500.jpg/melon/resize/120/quality/80/optimize"),
    ChartMusicUiData(10, "소나기", "이클립스 (ECLIPSE)", "선재 업고 튀어 OST Part 1", "https://cdnimg.melon.co.kr/cm2/album/images/114/59/325/11459325_20240405171054_500.jpg/melon/resize/120/quality/80/optimize"),
)

val songMockData = listOf(
    SongUiData(
        id = "1",
        title = "비가 내리는 날에는",
        artistName = "윤하",
        albumName = "A Perfect Day to Say I Love You",
        coverImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
        href = "https://music.apple.com/kr/song/1",
        genres = listOf("Ballad", "K-Pop"),
        durationMillis = 230000L
    ),
    SongUiData(
        id = "2",
        title = "주저하는 연인들을 위해",
        artistName = "잔나비",
        albumName = "전설",
        coverImg = "https://image.bugsm.co.kr/album/images/500/202371/20237198.jpg?version=20210422183751.0",
        href = "https://music.apple.com/kr/song/2",
        genres = listOf("Indie", "K-Pop"),
        durationMillis = 240000L
    ),
    SongUiData(
        id = "3",
        title = "Boom Boom Bass",
        artistName = "RIIZE",
        albumName = "Boom Boom Bass",
        coverImg = "https://cdnimg.melon.co.kr/cm2/album/images/114/56/179/11456179_20240617152303_500.jpg",
        href = "https://music.apple.com/kr/song/3",
        genres = listOf("Dance", "Pop"),
        durationMillis = 210000L
    ),
    SongUiData(
        id = "4",
        title = "사막에서 꽃을 피우듯",
        artistName = "우디 (Woody)",
        albumName = "사막에서 꽃을 피우듯",
        coverImg = "https://cdnimg.melon.co.kr/cm2/album/images/112/85/867/11285867_20230713162404_500.jpg",
        href = "https://music.apple.com/kr/song/4",
        genres = listOf("Ballad", "Acoustic"),
        durationMillis = 200000L
    ),
    SongUiData(
        id = "5",
        title = "퀸카 (Queencard)",
        artistName = "(여자)아이들",
        albumName = "I NEVER DIE",
        coverImg = "https://cdnimg.melon.co.kr/cm2/album/images/112/40/232/11240232_20230509151820_500.jpg",
        href = "https://music.apple.com/kr/song/5",
        genres = listOf("Pop", "K-Pop"),
        durationMillis = 250000L
    ),
    SongUiData(
        id = "6",
        title = "밤양갱",
        artistName = "비비 (BIBI)",
        albumName = "BIBI",
        coverImg = "https://cdnimg.melon.co.kr/cm2/album/images/114/15/997/11415997_20240226162412_500.jpg",
        href = "https://music.apple.com/kr/song/6",
        genres = listOf("R&B", "Soul"),
        durationMillis = 180000L
    ),
    SongUiData(
        id = "7",
        title = "모든 날, 모든 순간 (Every day, Every Moment)",
        artistName = "폴킴",
        albumName = "호텔 델루나 OST Part.3",
        coverImg = "https://cdnimg.melon.co.kr/cm/album/images/101/49/492/10149492_500.jpg",
        href = "https://music.apple.com/kr/song/7",
        genres = listOf("Ballad", "OST"),
        durationMillis = 260000L
    ),
    SongUiData(
        id = "8",
        title = "슬픈 초대장",
        artistName = "순순희 (지환)",
        albumName = "슬픈 초대장",
        coverImg = "https://image.bugsm.co.kr/album/images/500/40943/4094328.jpg?version=20240125010600.0",
        href = "https://music.apple.com/kr/song/8",
        genres = listOf("Ballad"),
        durationMillis = 220000L
    ),
    SongUiData(
        id = "9",
        title = "CRAZY",
        artistName = "LE SSERAFIM (르세라핌)",
        albumName = "CRAZY",
        coverImg = "https://cdnimg.melon.co.kr/cm2/album/images/115/79/884/11579884_20240830100608_500.jpg",
        href = "https://music.apple.com/kr/song/9",
        genres = listOf("Dance", "Pop"),
        durationMillis = 230000L
    ),
    SongUiData(
        id = "10",
        title = "After LIKE",
        artistName = "IVE (아이브)",
        albumName = "LOVE DIVE",
        coverImg = "https://image.bugsm.co.kr/album/images/500/40789/4078936.jpg?version=20220824005434.0",
        href = "https://music.apple.com/kr/song/10",
        genres = listOf("Pop", "K-Pop"),
        durationMillis = 210000L
    )
)