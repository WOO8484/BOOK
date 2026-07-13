# BOOKA_PART1_SCREEN_CHECKLIST.md

지시서 7 필수 화면 대조표. "존재"는 실제 소스 파일이 저장소에 있다는 뜻이며,
Gradle 컴파일로 검증하지는 못했다(상단 고지 참조).

| 필수 화면 | 존재 | 구현 위치 |
|---|---|---|
| 서재 메인 | O | feature/library/.../LibraryScreen.kt |
| 검색 | O | feature/library/.../LibrarySearchScreen.kt |
| 필터·정렬 | O | LibraryScreen.kt 내 정렬 바텀시트(BookaBottomSheet 사용) |
| 가져오기 메인 | O | feature/import/.../ImportScreen.kt |
| 가져오기 진행 화면 UI | O | feature/import/.../ImportProgressScreen.kt |
| 메타 분석 UI | O | feature/metadata/.../MetadataAnalysisScreen.kt |
| 메타 후보 선택 UI | O | feature/metadata/.../MetadataCandidateSelectScreen.kt |
| 직접 수정 UI | O | feature/metadata/.../MetadataManualEditScreen.kt |
| 파일명 변경 미리보기 UI | O | feature/import/.../RenamePreviewScreen.kt |
| 작품 상세 | O | feature/library/.../WorkDetailScreen.kt |
| 소설 뷰어 UI | O | feature/reader-novel/.../NovelReaderScreen.kt |
| 만화 뷰어 UI | O | feature/reader-comic/.../ComicReaderScreen.kt |
| 설정 메인 | O | feature/settings/.../SettingsMainScreen.kt |
| 책장 및 표시 | O | feature/settings/.../ShelfDisplaySettingsScreen.kt |
| 메타정보 설정 | O | feature/settings/.../MetadataProviderSettingsScreen.kt |
| 뷰어 설정 | O | feature/settings/.../ReaderDefaultsSettingsScreen.kt |
| 저장 및 관리 | O | feature/settings/.../StorageSettingsScreen.kt |
| 기타 | O | feature/settings/.../AboutScreen.kt |
| 공통 팝업 / 안내 다이얼로그 | O | core/ui/.../NotYetImplementedDialog.kt |
| 바텀시트 | O | core/ui/.../BookaBottomSheet.kt |
| 확인 다이얼로그 | O | core/ui/.../ConfirmDialog.kt |
| 빈 상태 | O | core/ui/.../EmptyState.kt |
| 오류 상태 | O | core/ui/.../ErrorState.kt |
| 로딩 상태 | O | core/ui/.../LoadingState.kt |

## 잔상·중복 이동 검증(지시서 6.1)

- `BookaBottomBar`는 공식 Compose Navigation 패턴(`launchSingleTop` + `restoreState` +
  `popUpTo(startDestination){ saveState = true }`)으로 구현해 동일 탭 재선택 시 중복
  destination을 만들지 않는다 (app/src/main/kotlin/.../navigation/BookaBottomBar.kt).
- 이를 자동 검증하는 계측 테스트를 작성했다: `app/src/androidTest/.../BookaNavigationTest.kt`
  (하단 메뉴 50회 반복 전환, 동일 메뉴 20회 반복 클릭). **다만 에뮬레이터/기기가 없어
  실제로 실행하지는 못했다.** 코드 검토로는 로직이 지시서 요건에 부합하나, 실기 실행 검증은
  미실시로 기록한다.
- 회전 중 전환, TalkBack 접근성 검사, 시스템 글자 확대(100/130/200%) 등 실기 항목은
  에뮬레이터·기기가 없어 전부 **미실시**로 기록한다(지시서 6.2 "미실시 기록" 규정에 따름).

## 하단 메뉴 숨김(지시서 3.1)

- `BookaApp.kt`에서 `NovelReader`/`ComicReader` destination일 때 `NavigationBar`를 렌더링하지 않도록 구현.
