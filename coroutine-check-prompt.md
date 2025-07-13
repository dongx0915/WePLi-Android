# Kotlin 코루틴 모범 사례 검증 가이드

## 개요
이 문서는 Kotlin 코루틴 코드를 자동으로 분석하여 모범 사례 위반 사항을 찾아내기 위한 검증 규칙들을 정의합니다. 각 규칙은 패턴 매칭과 정적 분석으로 검출 가능하도록 구성되어 있습니다.

## 검증 규칙

### 1. ASYNC_AWAIT_ANTIPATTERN
**설명**: `async { }.await()` 안티패턴 검출
**심각도**: WARNING
**검출 패턴**:
```kotlin
// 검출 대상
async { /* 단일 표현식 */ }.await()
```
**검출 방법**: 
- `async` 블록 내부에 단순 함수 호출만 있고 바로 `.await()` 호출하는 패턴
- coroutineScope 내에서 단일 async 사용 후 즉시 await 호출

### 2. MISSING_COROUTINE_SCOPE
**설명**: 여러 비동기 작업을 병렬로 실행할 때 coroutineScope 누락
**심각도**: INFO
**검출 패턴**:
```kotlin
// 검출 대상 - coroutineScope 없이 여러 async 사용
suspend fun example() {
    val a = async { taskA() }  // coroutineScope가 필요
    val b = async { taskB() }
    // ...
}
```

### 3. WRONG_CONTEXT_USAGE
**설명**: withContext(EmptyCoroutineContext) 대신 coroutineScope 사용 권장
**심각도**: INFO
**검출 패턴**:
```kotlin
withContext(EmptyCoroutineContext)
```

### 4. AVOID_MAP_AWAIT
**설명**: `map { it.await() }` 대신 `awaitAll()` 사용 권장
**심각도**: WARNING
**검출 패턴**:
```kotlin
// 검출 대상
collection.map { it.await() }
// 권장사항
awaitAll(*collection.toTypedArray())
```

### 5. MISSING_DISPATCHER_CONTEXT
**설명**: 블로킹 함수나 CPU 집약적 작업에서 적절한 디스패처 누락
**심각도**: ERROR
**검출 패턴**:
- 파일 I/O, 네트워크 호출 등에서 `withContext(Dispatchers.IO)` 누락
- CPU 집약적 작업에서 `withContext(Dispatchers.Default)` 누락
**키워드**: `Thread.sleep`, `readText`, `writeText`, `httpClient`, `retrofit`

### 6. USE_MAIN_IMMEDIATE
**설명**: Dispatchers.Main 대신 Dispatchers.Main.immediate 사용 권장
**심각도**: INFO
**검출 패턴**:
```kotlin
withContext(Dispatchers.Main)  // 검출 대상
// 권장: withContext(Dispatchers.Main.immediate)
```

### 7. MISSING_YIELD_IN_HEAVY_FUNCTION
**설명**: CPU 집약적 함수에서 yield() 호출 누락
**심각도**: WARNING
**검출 조건**:
- 긴 루프 (for, while) 내에서 복잡한 연산
- `withContext(Dispatchers.Default)` 내부의 긴 작업
- `yield()` 호출이 없는 경우

### 8. IMPROPER_JOB_USAGE
**설명**: Job() 컨텍스트의 부적절한 사용
**심각도**: ERROR
**검출 패턴**:
```kotlin
// 검출 대상
withContext(Job()) { }
CoroutineScope(Job())  // SupervisorJob() 권장
```

### 9. SUPERVISOR_JOB_RECOMMENDATION
**설명**: CoroutineScope 생성 시 SupervisorJob 사용 권장
**심각도**: WARNING
**검출 패턴**:
```kotlin
CoroutineScope(Job())  // 검출 대상
// 권장: CoroutineScope(SupervisorJob())
```

### 10. SCOPE_CANCELLATION_PATTERN
**설명**: 스코프 취소 시 cancel() 대신 cancelChildren() 권장
**심각도**: INFO
**검출 패턴**:
```kotlin
scope.cancel()  // 검출 대상
// 권장: scope.coroutineContext.cancelChildren()
```

### 11. GLOBAL_SCOPE_USAGE
**설명**: GlobalScope 사용 금지
**심각도**: ERROR
**검출 패턴**:
```kotlin
GlobalScope.launch
GlobalScope.async
```

### 12. UNNECESSARY_JOB_BUILDER
**설명**: 불필요한 Job() 빌더 사용
**심각도**: WARNING
**검출 패턴**:
- 스코프 생성 외의 목적으로 `Job()` 생성
- 단순 작업 관리 목적의 Job 생성

### 13. SUSPEND_FUNCTION_RETURNING_FLOW
**설명**: Flow 반환 함수가 suspend 함수로 정의된 경우
**심각도**: ERROR
**검출 패턴**:
```kotlin
suspend fun getData(): Flow<Data>  // 검출 대상
// 권장: fun getData(): Flow<Data>
```

### 14. FLOW_VS_SUSPEND_FUNCTION
**설명**: 단일 값에 Flow 사용 시 suspend 함수 권장
**심각도**: INFO
**검출 조건**:
- Repository 패턴에서 단일 데이터 조회에 Flow 사용
- 함수명이 `get...`, `fetch...`, `load...`로 시작하는 경우

## HTML 리포트 생성 스펙

### 리포트 구조
```html
<!DOCTYPE html>
<html>
<head>
    <title>Kotlin 코루틴 모범 사례 검증 리포트</title>
    <style>
        /* CSS 스타일링 */
        .error { color: #d32f2f; }
        .warning { color: #f57c00; }
        .info { color: #1976d2; }
        .file-section { margin: 20px 0; }
        .violation { margin: 10px 0; padding: 10px; border-left: 4px solid; }
        .code-snippet { background: #f5f5f5; padding: 10px; font-family: monospace; }
    </style>
</head>
<body>
    <h1>코루틴 모범 사례 검증 리포트</h1>
    
    <div class="summary">
        <h2>요약</h2>
        <p>총 {total_files}개 파일 분석</p>
        <p>오류: {error_count}개, 경고: {warning_count}개, 정보: {info_count}개</p>
    </div>
    
    <div class="violations">
        <h2>위반 사항</h2>
        <!-- 파일별 위반 사항 -->
    </div>
</body>
</html>
```

### 위반 사항 템플릿
```html
<div class="file-section">
    <h3>{file_path}</h3>
    
    <div class="violation {severity_class}">
        <h4>{rule_name}: {description}</h4>
        <p><strong>라인 {line_number}:</strong> {violation_message}</p>
        <div class="code-snippet">
            <pre>{code_snippet}</pre>
        </div>
        <p><strong>권장 사항:</strong> {recommendation}</p>
    </div>
</div>
```

## 검증 스크립트 가이드

Claude Code에서 다음과 같이 요청하세요:

```
이 Kotlin 코루틴 모범 사례 가이드를 사용해서 내 프로젝트를 분석하고, 
위반 사항을 HTML 리포트로 생성해줘. 

HTML 작성시 Tailwind CSS의 컴포넌트를 사용하여 일관성 있게 출력하고, 코드는 Prism을 통해 강조해줘.

분석할 대상:
- .kt 파일의 코루틴 관련 코드
- suspend 함수 정의
- 코루틴 빌더 사용 패턴
- Flow 사용 패턴

출력 형식:
- HTML 파일로 리포트 생성
- 심각도별 색상 구분
- 파일별, 라인별 위반 사항 정리
- 각 위반에 대한 구체적 권장사항 포함
```

## 추가 검증 포인트

### 네이밍 컨벤션
- suspend 함수는 동사형 이름 사용
- Flow 반환 함수는 `observe...`, `watch...` 등의 접두사 사용

### 아키텍처 패턴
- Repository에서 적절한 반환 타입 사용
- ViewModel에서 적절한 스코프 사용
- UI 업데이트 시 Main 디스패처 사용

이 가이드를 사용하면 프로젝트의 코루틴 코드 품질을 체계적으로 검증하고 개선점을 찾을 수 있습니다.