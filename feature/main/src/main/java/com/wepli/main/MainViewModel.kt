package com.wepli.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.Artist
import model.MusicData
import model.RecommendPlaylist
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _musicList = MutableStateFlow<List<MusicData>>(emptyList())
    val musicList: StateFlow<List<MusicData>> = _musicList.asStateFlow()

    private val _artistList = MutableStateFlow<List<Artist>>(emptyList())
    val artistList: StateFlow<List<Artist>> = _artistList.asStateFlow()

    private val _recommendPlaylists = MutableStateFlow<List<RecommendPlaylist>>(emptyList())
    val recommendPlaylists: StateFlow<List<RecommendPlaylist>> = _recommendPlaylists.asStateFlow()

    init {
        _musicList.value = listOf(
            MusicData(1, "HAPPY", "DAY6 (데이식스)", "Fourever", "https://cdnimg.melon.co.kr/cm2/album/images/114/44/397/11444397_20240318115810_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(2, "Welcome to the Show", "DAY6 (데이식스)", "Fourever", "https://cdnimg.melon.co.kr/cm2/album/images/114/44/397/11444397_20240318115810_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(3, "Supernova", "aespa", "Armageddon - The 1st Album", "https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(4, "한 페이지가 될 수 있게", "DAY6 (데이식스)", "The Book of Us : Gravity", "https://cdnimg.melon.co.kr/cm/album/images/103/07/346/10307346_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(5, "녹아내려요", "DAY6 (데이식스)", "Band Aid", "https://cdnimg.melon.co.kr/cm2/album/images/115/80/616/11580616_20240830171855_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(6, "클락션 (Klaxon)", "(여자)아이들", "I SWAY", "https://cdnimg.melon.co.kr/cm2/album/images/115/33/515/11533515_20240708112323_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(7, "How Sweet", "NewJeans", "How Sweet", "https://cdnimg.melon.co.kr/cm2/album/images/114/75/749/11475749_20240524105642_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(8, "고민중독", "QWER", "1st Mini Album 'MANITO'", "https://cdnimg.melon.co.kr/cm2/album/images/114/54/681/11454681_20240328174504_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(9, "예뻤어", "DAY6 (데이식스)", "Every DAY6 February", "https://cdnimg.melon.co.kr/cm/album/images/100/36/099/10036099_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(10, "소나기", "이클립스 (ECLIPSE)", "선재 업고 튀어 OST Part 1", "https://cdnimg.melon.co.kr/cm2/album/images/114/59/325/11459325_20240405171054_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(11, "Supernatural", "NewJeans", "Supernatural", "https://cdnimg.melon.co.kr/cm2/album/images/115/14/014/11514014_20240621110830_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(12, "내 이름 맑음", "QWER", "2nd Mini Album 'Algorithm's Blossom'", "https://cdnimg.melon.co.kr/cm2/album/images/115/80/909/11580909_20240923131754_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(13, "Sticky", "KISS OF LIFE", "Sticky", "https://cdnimg.melon.co.kr/cm2/album/images/115/25/661/11525661_20240628155508_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(14, "나는 아픈 건 딱 질색이니까", "(여자)아이들", "2", "https://cdnimg.melon.co.kr/cm2/album/images/114/02/655/11402655_20240129121016_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(15, "첫 만남은 계획대로 되지 않아", "TWS (투어스)", "TWS 1st Mini Album ‘Sparkling Blue’", "https://cdnimg.melon.co.kr/cm2/album/images/113/91/902/11391902_20240122132041_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(16, "Supersonic", "프로미스나인", "Supersonic", "https://cdnimg.melon.co.kr/cm2/album/images/115/64/698/11564698_20240812100922_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(17, "Armageddon", "aespa", "Armageddon - The 1st Album", "https://cdnimg.melon.co.kr/cm2/album/images/114/87/023/11487023_20240527154018_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(18, "해야 (HEYA)", "IVE (아이브)", "IVE SWITCH", "https://cdnimg.melon.co.kr/cm2/album/images/114/75/530/11475530_20240430093854_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(19, "천상연", "이창섭", "천상연 (웹툰 '선녀외전' X 이창섭 (LEE CHANGSUB))", "https://cdnimg.melon.co.kr/cm2/album/images/114/21/941/11421941_20240220105008_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(20, "에피소드", "이무진", "에피소드", "https://cdnimg.melon.co.kr/cm2/album/images/113/82/987/11382987_20231212165916_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(21, "Bubble Gum", "NewJeans", "How Sweet", "https://cdnimg.melon.co.kr/cm2/album/images/114/75/749/11475749_20240524105642_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(22, "Hype Boy", "NewJeans", "NewJeans 1st EP 'New Jeans'", "https://cdnimg.melon.co.kr/cm2/album/images/110/11/565/11011565_20220801102637_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(23, "I AM", "IVE (아이브)", "I've IVE", "https://cdnimg.melon.co.kr/cm2/album/images/112/11/297/11211297_20230410151046_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(24, "비의 랩소디", "임재현", "비의 랩소디", "https://cdnimg.melon.co.kr/cm2/album/images/113/76/816/11376816_20231201120653_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(25, "Congratulations", "DAY6 (데이식스)", "The Day", "https://cdnimg.melon.co.kr/cm/album/images/026/38/778/2638778_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(26, "헤어지자 말해요", "박재정", "1집 Alone", "https://cdnimg.melon.co.kr/cm2/album/images/112/27/533/11227533_20230419162238_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(27, "Ditto", "NewJeans", "NewJeans 'OMG'", "https://cdnimg.melon.co.kr/cm2/album/images/111/27/145/11127145_20231213133532_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(28, "별별별 (See that?)", "NMIXX", "Fe3O4: STICK OUT", "https://cdnimg.melon.co.kr/cm2/album/images/115/68/571/11568571_20240814180403_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(29, "미안해 미워해 사랑해", "Crush", "눈물의 여왕 OST Part.4", "https://cdnimg.melon.co.kr/cm2/album/images/114/49/278/11449278_20240322170237_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(30, "To. X", "태연 (TAEYEON)", "To. X - The 5th Mini Album", "https://cdnimg.melon.co.kr/cm2/album/images/113/72/896/11372896_20231127104957_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(31, "주저하는 연인들을 위해", "잔나비", "전설", "https://cdnimg.melon.co.kr/cm/album/images/102/60/858/10260858_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(32, "내가 S면 넌 나의 N이 되어줘", "TWS (투어스)", "TWS 2nd Mini Album ‘SUMMER BEAT!’", "https://cdnimg.melon.co.kr/cm2/album/images/115/05/319/11505319_20240624143253_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(33, "ETA", "NewJeans", "NewJeans 2nd EP 'Get Up'", "https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(34, "너의 모든 순간", "성시경", "별에서 온 그대 OST Part.7", "https://cdnimg.melon.co.kr/cm/album/images/022/32/505/2232505_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(35, "Attention", "NewJeans", "NewJeans 1st EP 'New Jeans'", "https://cdnimg.melon.co.kr/cm2/album/images/110/11/565/11011565_20220801102637_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(36, "인사", "범진", "인사", "https://cdnimg.melon.co.kr/cm2/album/images/108/21/699/10821699_20231205103724_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(37, "Super Shy", "NewJeans", "NewJeans 2nd EP 'Get Up'", "https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(38, "MEOW", "MEOVV (미야오)", "MEOW", "https://cdnimg.melon.co.kr/cm2/album/images/115/86/554/11586554_20240906095540_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(39, "Drama", "aespa", "Drama - The 4th Mini Album", "https://cdnimg.melon.co.kr/cm2/album/images/113/62/544/11362544_20231110142622_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(40, "CRAZY", "LE SSERAFIM (르세라핌)", "CRAZY", "https://cdnimg.melon.co.kr/cm2/album/images/115/79/884/11579884_20240830100608_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(41, "사랑은 늘 도망가", "임영웅", "신사와 아가씨 OST Part.2", "https://cdnimg.melon.co.kr/cm2/album/images/107/35/654/10735654_20211008114339_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(42, "청혼하지 않을 이유를 못 찾았어", "이무진", "청혼하지 않을 이유를 못 찾았어", "https://cdnimg.melon.co.kr/cm2/album/images/114/55/285/11455285_20240401151801_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(43, "LOVE DIVE", "IVE (아이브)", "LOVE DIVE", "https://cdnimg.melon.co.kr/cm2/album/images/109/09/179/10909179_20220405103521_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(44, "Spicy", "aespa", "MY WORLD - The 3rd Mini Album", "https://cdnimg.melon.co.kr/cm2/album/images/112/36/264/11236264_20230508184331_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(45, "SHEESH", "BABYMONSTER", "BABYMONS7ER", "https://cdnimg.melon.co.kr/cm2/album/images/114/54/802/11454802_20240401174250_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(46, "OMG", "NewJeans", "NewJeans 'OMG'", "https://cdnimg.melon.co.kr/cm2/album/images/111/27/145/11127145_20231213133532_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(47, "꿈", "태연 (TAEYEON)", "웰컴투 삼달리 OST Part.3", "https://cdnimg.melon.co.kr/cm2/album/images/113/85/577/11385577_20231215144114_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(48, "그랬나봐", "유회승 (엔플라잉)", "선재 업고 튀어 OST Part 6", "https://cdnimg.melon.co.kr/cm2/album/images/114/80/740/11480740_20240503112506_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(49, "Small girl (feat. 도경수(D.O.))", "이영지", "16 Fantasy", "https://cdnimg.melon.co.kr/cm2/album/images/115/17/951/11517951_20240620173412_500.jpg/melon/resize/120/quality/80/optimize"),
            MusicData(50, "Magnetic", "아일릿(ILLIT)", "SUPER REAL ME", "https://cdnimg.melon.co.kr/cm2/album/images/114/50/069/11450069_20240325110702_500.jpg/melon/resize/120/quality/80/optimize")
        )
        _artistList.value = listOf(
            Artist("윤하(Younha/ユンナ)", "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"),
            Artist("아이유(IU)", "https://image.bugsm.co.kr/artist/images/1000/800491/80049126_068.jpg?version=301040&d=20210325154659"),
            Artist("이창섭", "https://image.bugsm.co.kr/artist/images/1000/801234/80123432.jpg?version=133225&d=20240902175949"),
            Artist("BIGBANG (빅뱅)", "https://image.bugsm.co.kr/artist/images/1000/800200/80020023_078.jpg?version=253655&d=20190704103532"),
            Artist("츄 (CHUU)", "https://image.bugsm.co.kr/artist/images/1000/802980/80298005_010.jpg?version=383827&d=20231018020427"),
            Artist("(여자)아이들", "https://image.bugsm.co.kr/artist/images/1000/200564/20056456.jpg?version=227124&d=20240703113218"),
            Artist("로이킴", "https://image.bugsm.co.kr/artist/images/1000/801377/80137715.jpg?version=112079&d=20240304183844"),
            Artist("이승기", "https://image.bugsm.co.kr/artist/images/1000/721/72184_041.jpg?version=292892&d=20201211184753"),
            Artist("aespa", "https://image.bugsm.co.kr/artist/images/1000/803473/80347326_049.jpg?version=402792&d=20240510104032")
        )
        _recommendPlaylists.value = listOf(
            RecommendPlaylist("To. X","https://cdnimg.melon.co.kr/cm2/album/images/113/72/896/11372896_20231127104957_500.jpg"),
            RecommendPlaylist("주저하는 연인들을 위해","https://cdnimg.melon.co.kr/cm/album/images/102/60/858/10260858_500.jpg"),
            RecommendPlaylist("내가 S면 넌 나의 N이 되어줘","https://cdnimg.melon.co.kr/cm2/album/images/115/05/319/11505319_20240624143253_500.jpg"),
            RecommendPlaylist("ETA","https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg"),
            RecommendPlaylist("너의 모든 순간","https://cdnimg.melon.co.kr/cm/album/images/022/32/505/2232505_500.jpg"),
            RecommendPlaylist("Attention","https://cdnimg.melon.co.kr/cm2/album/images/112/81/456/11281456_20230706180841_500.jpg"),
            RecommendPlaylist("Super Shy","https://cdnimg.melon.co.kr/cm2/album/images/113/72/896/11372896_20231127104957_500.jpg"),
            RecommendPlaylist("MEOW","https://cdnimg.melon.co.kr/cm2/album/images/115/86/554/11586554_20240906095540_500.jpg"),
            RecommendPlaylist("CRAZY","https://cdnimg.melon.co.kr/cm2/album/images/115/79/884/11579884_20240830100608_500.jpg"),
            RecommendPlaylist("Drama","https://cdnimg.melon.co.kr/cm2/album/images/113/62/544/11362544_20231110142622_500.jpg"),
        )
    }
}