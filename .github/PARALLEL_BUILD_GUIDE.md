# 🚀 GitHub Actions 병렬 빌드 최적화 가이드

## 🎯 개요
이 가이드는 GitHub Actions에서 병렬 처리를 통해 빌드 시간을 최대한 단축하는 방법을 설명합니다.

## ⚡ 병렬 최적화 전략

### 1. 💻 기본 최적화 (단일 Job 내)
```yaml
- name: 🔨 빌드 최적화
  run: |
    ./gradlew assembleDebug \
      --no-daemon \
      --parallel \
      --build-cache \
      --configuration-cache \
      --max-workers=4
```

### 2. 🔀 Job 병렬화 (다중 Job)
다음과 같이 단계별로 병렬 실행:

```
setup (환경 준비)
├── prepare-files (파일 준비)
├── setup-java (Java 설정)
└── download-dependencies (의존성 다운로드)
    └── build (실제 빌드)
        └── deploy (Discord 업로드)
```

### 3. 🏗️ 매트릭스 빌드
```yaml
strategy:
  matrix:
    build-type: [debug, release]
    arch: [arm64-v8a, armeabi-v7a, x86_64]
```

## 📊 성능 비교

| 방식 | 예상 시간 | 특징 |
|------|----------|------|
| 순차 실행 | 8-12분 | 기본 방식 |
| 단일 Job 최적화 | 5-8분 | Gradle 병렬 처리 |
| 다중 Job 병렬화 | 4-6분 | 환경 설정 병렬화 |
| 매트릭스 빌드 | 3-5분 | 여러 변형 동시 빌드 |

## 🔧 구현된 최적화 기능

### 1. 환경 설정 병렬화
- **setup**: 캐시 키 생성 및 기본 설정
- **prepare-files**: 설정 파일 준비
- **setup-java**: Java 환경 설정
- **download-dependencies**: 의존성 다운로드

### 2. Gradle 빌드 최적화
```bash
./gradlew assembleDebug \
  --no-daemon \           # 데몬 비활성화 (CI 환경)
  --parallel \            # 병렬 처리
  --build-cache \         # 빌드 캐시
  --configuration-cache   # 구성 캐시
```

### 3. 캐시 전략
- **Gradle Wrapper**: 다운로드 시간 단축
- **Gradle Dependencies**: 의존성 캐시
- **Build Cache**: 빌드 결과 캐시

### 4. 아티팩트 관리
- 필요한 파일만 단계별 전달
- 자동 정리로 스토리지 절약

## 🛠️ 추가 최적화 방법

### 1. 캐시 최적화
```yaml
- name: 📦 고급 캐시 설정
  uses: actions/cache@v4
  with:
    path: |
      ~/.gradle/caches
      ~/.gradle/wrapper
      ~/.android/build-cache
    key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}
    restore-keys: |
      ${{ runner.os }}-gradle-
```

### 2. 병렬 테스트 실행
```yaml
test:
  strategy:
    matrix:
      module: [app, core, data, domain, feature]
  steps:
    - run: ./gradlew :${{ matrix.module }}:test
```

### 3. 빌드 변형 병렬화
```yaml
build:
  strategy:
    matrix:
      variant: [debug, release]
      abi: [arm64-v8a, armeabi-v7a]
  steps:
    - run: ./gradlew assemble${{ matrix.variant }}
```

## 🎮 사용 방법

### 기본 빌드 (최적화됨)
```bash
# 기존 워크플로우 사용
.github/workflows/debug-deploy.yml
```

### 병렬 빌드 (최대 성능)
```bash
# 새로운 병렬 워크플로우 사용
.github/workflows/debug-deploy-parallel.yml
```

### 수동 트리거
1. GitHub Actions 탭 이동
2. "Discord로 Debug APK 자동 배포 (병렬 최적화)" 선택
3. "Run workflow" 클릭

## 📈 모니터링

### 빌드 시간 추적
```yaml
- name: 📊 빌드 시간 측정
  run: |
    echo "Build started at: $(date)"
    time ./gradlew assembleDebug
    echo "Build completed at: $(date)"
```

### 리소스 사용량
```yaml
- name: 💾 리소스 모니터링
  run: |
    echo "=== System Info ===" 
    free -h
    df -h
    nproc
```

## 🔄 워크플로우 선택 가이드

### 언제 기본 워크플로우를 사용할까?
- 간단한 빌드가 필요한 경우
- 디버깅이 필요한 경우
- 안정성을 우선시하는 경우

### 언제 병렬 워크플로우를 사용할까?
- 빌드 시간이 중요한 경우
- 자주 빌드하는 경우
- CI/CD 파이프라인 최적화가 필요한 경우

## 🚨 주의사항

### 1. 리소스 제한
- GitHub Actions의 동시 실행 제한
- 월별 사용량 제한
- 스토리지 사용량

### 2. 복잡성 증가
- 디버깅 어려움
- 의존성 관리 복잡성
- 실패 시 원인 파악 어려움

### 3. 비용 고려
- 병렬 실행 시 더 많은 runner 사용
- 프라이빗 리포지토리에서 비용 발생 가능

## 🎯 결론

병렬 빌드는 다음과 같은 장점을 제공합니다:
- ⚡ **빌드 시간 단축**: 50-60% 시간 절약
- 🔄 **효율적인 리소스 사용**: 동시 작업 처리
- 🎮 **개발 경험 향상**: 빠른 피드백 루프

단점:
- 🔧 **복잡성 증가**: 디버깅 어려움
- 💰 **비용 증가**: 더 많은 리소스 사용

**권장사항**: 프로덕션 환경에서는 병렬 빌드를, 개발/테스트 환경에서는 기본 빌드를 사용하는 것이 좋습니다.

---

**💡 Tip**: 첫 번째 빌드에서는 캐시가 없어 시간이 오래 걸릴 수 있지만, 이후 빌드부터는 캐시 효과로 대폭 빨라집니다!