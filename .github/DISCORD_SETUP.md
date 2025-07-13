# Discord 자동 배포 설정 가이드

## 🎯 개요
이 가이드는 GitHub Actions를 통해 WePLi Android Debug APK를 Discord 채널로 자동 배포하는 설정 방법을 설명합니다.

## 📋 필요한 설정

### 1. Discord Webhook URL 생성

1. **Discord 서버 설정**
   - 배포용 채널 생성 (예: `#apk-배포`, `#development`)
   - 채널 설정 → 연동 → 웹후크 생성

2. **웹후크 설정**
   - 웹후크 이름: `WePLi Android Deploy Bot`
   - 아바타: 원하는 이미지 (선택사항)
   - 웹후크 URL 복사 및 저장

### 2. GitHub Repository Secrets 설정

GitHub Repository → Settings → Secrets and variables → Actions에서 다음 secrets을 추가:

#### 필수 Secrets
```
DISCORD_WEBHOOK_URL=https://discord.com/api/webhooks/your-webhook-url
```

#### API 관련 Secrets
```
# Supabase 설정
SUPABASE_URL=https://tnzaalcaqnanyprzbyhs.supabase.co
SUPABASE_ANON_KEY=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRuemFhbGNhcW5hbnlwcnpieWhzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE5NDE2NDUsImV4cCI6MjA0NzUxNzY0NX0.Fw-nUvprxkl1ZpoEXho2XA-yUKftrYFMBfb2sBzBdMw
SUPABASE_CLIENT_ID=103843519167-irrt7h4sb995p500tlmohoh5lp21c2ss.apps.googleusercontent.com

# Apple Music API 설정
APPLE_MUSIC_URL=https://api.music.apple.com/
APPLE_MUSIC_API_TOKEN=Bearer eyJraWQiOiI3QlU4R1pBNDREIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiI4UTRIN1gzUTU4IiwiZXhwIjoxNzQ5MjIxMDUyLCJpYXQiOjE3NDA1ODEwNTJ9.2IEUFOf1o0K6vciuYU_MLhHUpqJUw0IzUfk8ETW_k_-5VdAE2dMsv7eMfNpF0lHHpTmdvBGGXxne0coajSSeJg

# Postman Mock API 설정
POSTMAN_URL=https://c5d99f29-4f14-416b-9baa-c691ac5fe558.mock.pstmn.io

# Google Services (선택적)
GOOGLE_SERVICES_JSON=base64로 인코딩된 google-services.json 내용
```

**⚠️ 주의사항**: 위의 API 키들은 예시로 제공된 것이며, 실제 운영 환경에서는 본인의 API 키를 사용해야 합니다.

### 3. Google Services JSON 설정 (Firebase 사용 시)

1. **파일 인코딩**
   ```bash
   # macOS/Linux
   base64 -i app/google-services.json
   
   # 또는 온라인 Base64 인코더 사용
   ```

2. **GitHub Secrets에 추가**
   - Secret 이름: `GOOGLE_SERVICES_JSON`
   - 값: 인코딩된 문자열 전체

## 🚀 배포 트리거

워크플로우는 다음 상황에서 자동 실행됩니다:

### 1. 자동 트리거
- `release/` 패턴의 브랜치가 생성될 때 (예: `release/1.0.0`, `release/v2.1.0`)

### 2. 수동 트리거
- GitHub Actions 탭에서 "Discord로 Debug APK 자동 배포" 워크플로우 수동 실행

## 📱 배포 결과

### Discord 메시지 내용
배포 완료 시 Discord 채널에 다음 정보가 포함된 embed 메시지가 전송됩니다:

- 📱 **APK 정보**: 파일명, 크기
- 🌿 **브랜치**: 빌드된 Release 브랜치명
- 📅 **빌드 시간**: 빌드 완료 시간
- 📎 **APK 파일**: 다운로드 가능한 APK 첨부

## ⚙️ 워크플로우 구성

### 빌드 환경
- **OS**: Ubuntu Latest
- **Java**: JDK 17 (AdoptOpenJDK)
- **Gradle**: 프로젝트 설정된 버전
- **캐싱**: Gradle 의존성 캐싱 활성화

### 빌드 과정
1. 📥 저장소 체크아웃
2. ☕ Java 17 설정
3. 💾 Gradle 캐시 복원
4. 📄 google-services.json 디코딩 (필요 시)
5. ⚙️ local.properties 생성 (모든 API 키 포함)
6. 🔨 Debug APK 빌드
7. 📱 APK 정보 수집
8. 🚀 Discord로 업로드

## 🔧 커스터마이징

### Discord 메시지 수정
`.github/workflows/debug-deploy.yml` 파일의 `EMBED_JSON` 섹션에서 메시지 내용을 수정할 수 있습니다.

### 빌드 조건 변경
`on:` 섹션에서 트리거 조건을 수정할 수 있습니다:

```yaml
on:
  create:
    branches:
      - 'release/**'      # release 브랜치 패턴
      - 'hotfix/**'       # hotfix 브랜치 패턴 추가
  workflow_dispatch:      # 수동 트리거
```

### APK 변형 변경
다른 빌드 변형을 원하는 경우:

```yaml
- name: Build Release APK
  run: ./gradlew assembleRelease --stacktrace
```

## 🛠️ 문제 해결

### 일반적인 문제

1. **DISCORD_WEBHOOK_URL 오류**
   - GitHub Secrets에 올바른 webhook URL이 설정되었는지 확인
   - Discord에서 webhook이 활성화되어 있는지 확인

2. **빌드 실패**
   - `google-services.json` 파일이 올바르게 설정되었는지 확인
   - Supabase 관련 secrets이 필요한 경우 설정 확인

3. **APK 업로드 실패**
   - Discord 파일 크기 제한 (25MB) 확인
   - 네트워크 문제로 인한 일시적 실패 가능성

### 로그 확인
GitHub Actions 탭에서 각 step의 로그를 확인하여 구체적인 오류 원인을 파악할 수 있습니다.

## 📝 참고사항

- APK 파일은 Discord 서버에 영구적으로 저장됩니다
- 파일 크기 제한: Discord 25MB
- 개인정보나 민감한 정보가 포함되지 않도록 주의
- production 빌드의 경우 별도 워크플로우 구성 권장

## 🔄 업데이트

워크플로우 파일을 수정한 후에는:
1. 변경사항을 commit & push
2. 새로운 빌드에서 수정된 설정이 적용됨
3. 필요 시 수동으로 워크플로우 실행하여 테스트

---

**💡 Tip**: 처음 설정 후에는 수동 트리거로 테스트해보는 것을 권장합니다!