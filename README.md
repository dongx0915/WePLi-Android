# WePLi - Android <a href="https://wepli.site"><img src="https://github.com/WePLi-Team/WePLi-Resources/blob/feec3a5c3fac5ec04cf2d9e72943c772e3a21f7b/originals/brand/wepli-logo-small.png" align="left" width="100"></a>

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84?style=flat-square&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.20-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/compose-2025.01.01-4285F4?style=flat-square&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/min%20sdk-30-green?style=flat-square)](https://developer.android.com)


<br>

플레이리스트를 함께 만드는 음악 취향 공유 앱

## 주요 기능

- 음악 검색 및 재생
- 플레이리스트 생성/관리/공유
- 릴레이리스트 (실시간 참여형 플레이리스트)
- 커뮤니티 (포스트, 댓글, 스토리)
- 포토카드 생성
- 소셜 로그인 (Google, Facebook)

## 기술 스택

### UI
- Jetpack Compose (2025.01.01)
- Material Design 3
- Haze (Blur Effect)

### 아키텍처
- Clean Architecture
- MVI Pattern (Orbit MVI)
- Multi-Module Architecture
- Hilt (Dependency Injection)

### 백엔드 & 데이터
- Supabase (PostgreSQL, Realtime, Storage)
- Retrofit + OkHttp3
- Room Database
- DataStore

### 기타
- Firebase Cloud Messaging
- YouTube Player
- Coil (Image Loading)
- Kotlinx Serialization

## 프로젝트 구조

```
WePLi/
├── app/                    # 메인 애플리케이션
├── feature/                # Feature 모듈
│   ├── home/              # 홈 화면
│   ├── search/            # 검색
│   ├── playlist/          # 플레이리스트
│   ├── relaylist/         # 릴레이리스트
│   ├── community/         # 커뮤니티
│   ├── photocard/         # 포토카드
│   ├── song/              # 곡 정보
│   ├── mypage/            # 마이페이지
│   └── devmode/           # 개발자 모드
├── core/                   # 공통 모듈
│   ├── common/            # MVI 기본 클래스
│   ├── navigator/         # 네비게이션
│   ├── kotlin/            # Kotlin 유틸
│   └── resources/         # 리소스
├── domain/                 # 비즈니스 로직
├── data/                   # 데이터 계층
├── designsystem/           # UI 컴포넌트
├── shared/feature/         # 공유 UI 데이터
└── build-logic/            # Gradle Convention Plugins
```

## 개발 환경

- Android Studio Ladybug or later
- Kotlin 2.2.20
- JDK 17
- Min SDK 30 (Android 11)
- Target SDK 35 (Android 15)
