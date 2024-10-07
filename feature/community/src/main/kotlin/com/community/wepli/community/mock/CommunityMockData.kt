package com.community.wepli.community.mock

import model.community.Post
import model.music.Song

val songs = listOf(
    Song(
        title = "비가 내리는 날에는",
        artist = "윤하",
        albumCoverImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"
    ),
    Song(
        title = "주저하는 연인들을 위해",
        artist = "잔나비",
        albumCoverImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"
    ),
    Song(
        title = "Boom Boom Bass",
        artist = "RIIZE",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/114/56/179/11456179_20240617152303_500.jpg"
    ),
    Song(
        title = "사막에서 꽃을 피우듯",
        artist = "우디 (Woody)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/112/85/867/11285867_20230713162404_500.jpg"
    ),
    Song(
        title = "퀸카 (Queencard)",
        artist = "(여자)아이들",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/112/40/232/11240232_20230509151820_500.jpg"
    ),
    Song(
        title = "밤양갱",
        artist = "비비 (BIBI)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/114/15/997/11415997_20240226162412_500.jpg"
    ),
    Song(
        title = "모든 날, 모든 순간 (Every day, Every Moment)",
        artist = "폴킴",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm/album/images/101/49/492/10149492_500.jpg"
    ),
    Song(
        title = "슬픈 초대장",
        artist = "순순희 (지환)",
        albumCoverImg = "https://image.bugsm.co.kr/album/images/500/40943/4094328.jpg?version=20240125010600.0"
    ),
    Song(
        title = "CRAZY",
        artist = "LE SSERAFIM (르세라핌)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/115/79/884/11579884_20240830100608_500.jpg"
    ),
    Song(
        title = "I Don't Think That I Like Her",
        artist = "Charlie Puth",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/108/44/485/10844485_20221006154824_500.jpg"
    ),
    Song(
        title = "어떻게 이별까지 사랑하겠어, 널 사랑하는 거지",
        artist = "AKMU (악뮤)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/103/31/947/10331947_500.jpg"
    ),
    Song(
        title = "그녀가 웃었다",
        artist = "DAY6 (데이식스)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/115/80/616/11580616_20240830171855_500.jpg"
    ),
    Song(
        title = "After LIKE",
        artist = "IVE (아이브)",
        albumCoverImg = "https://image.bugsm.co.kr/album/images/500/40789/4078936.jpg?version=20220824005434.0"
    ),
    Song(
        title = "Live My Life",
        artist = "aespa",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg"
    ),
    Song(
        title = "Love Lee",
        artist = "AKMU (악뮤)",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/113/09/190/11309190_20230818161008_500.jpg"
    ),
    Song(
        title = "오래된 노래",
        artist = "허각",
        albumCoverImg = "https://cdnimg.melon.co.kr/cm2/album/images/115/13/169/11513169_20240614150349_500.jpg"
    ),
)

val posts = listOf(
    Post(
        title = "밤새면서 들을 수 있는 플리",
        content = "여러분은 코딩할 때 어떤 곡을 듣나요?! 댓글로 알려주세요!\n제가 주로 듣는 곡들을 플레이리스트로 만들어봤어요~\n\n같이 들으면 좋을만한 노래 추천해주세요!",
        author = "dlwlrma",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800491/80049126_068.jpg?version=301040&d=20210325154659",
        songList = songs
    ),
    Post(
        title = "요즘 최애 노래",
        content = "케이윌이랑 매드클라운 조합 너무 좋아요ㅠㅠ\n가볍게 듣기에도 좋고 은근 밝은 느낌이라서 더 좋아요\n\n다들 한 번씩 들어보세요~~~",
        author = "yoonha",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
        songList = songs.shuffled().take(1)
    ),
    Post(
        title = "이것도 들어봐ㅏㅏ",
        content = "이것도 들어봐바\n봄이면 꼭 들어야 돼 나만 들을 순 없어\n\n빨리 다들 한 번씩 듣고 댓글로 감상평 남겨ㅡㅡ",
        author = "yoonha",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
        songList = songs.shuffled().take(1)
    ),
    Post(
        title = "너무 심심해요",
        content = "주말에 하루종일 아무것도 할 게 없어서 심심하네요\n스피커도 새로 샀는데 들을 노래도 없고...\n\n혼자서 심심할 때 듣기 좋은 노래 추천 부탁드려요 :)",
        author = "roykim_official",
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/801377/80137715.jpg?version=112079&d=20240304183844",
        songList = emptyList()
    )
)