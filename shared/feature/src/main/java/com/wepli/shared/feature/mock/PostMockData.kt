package com.wepli.shared.feature.mock

import com.wepli.uimodel.community.PostUiData

val postMockData = listOf(
    PostUiData(
        title = "밤새면서 들을 수 있는 플리",
        content = "여러분은 코딩할 때 어떤 곡을 듣나요?! 댓글로 알려주세요!\n제가 주로 듣는 곡들을 플레이리스트로 만들어봤어요~\n\n같이 들으면 좋을만한 노래 추천해주세요!",
        author = "dlwlrma",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800491/80049126_068.jpg?version=301040&d=20210325154659",
        songList = songMockData
    ),
    PostUiData(
        title = "요즘 최애 노래",
        content = "케이윌이랑 매드클라운 조합 너무 좋아요ㅠㅠ\n가볍게 듣기에도 좋고 은근 밝은 느낌이라서 더 좋아요\n\n다들 한 번씩 들어보세요~~~",
        author = "yoonha",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
        songList = songMockData.shuffled().take(1)
    ),
    PostUiData(
        title = "이것도 들어봐ㅏㅏ",
        content = "이것도 들어봐바\n봄이면 꼭 들어야 돼 나만 들을 순 없어\n\n빨리 다들 한 번씩 듣고 댓글로 감상평 남겨ㅡㅡ",
        author = "yoonha",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
        songList = songMockData.shuffled().take(1)
    ),
    PostUiData(
        title = "너무 심심해요",
        content = "주말에 하루종일 아무것도 할 게 없어서 심심하네요\n스피커도 새로 샀는데 들을 노래도 없고...\n\n혼자서 심심할 때 듣기 좋은 노래 추천 부탁드려요 :)",
        author = "roykim_official",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/801377/80137715.jpg?version=112079&d=20240304183844",
        songList = emptyList()
    )
)