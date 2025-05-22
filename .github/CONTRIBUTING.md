
# 브랜치 전략
- `master`: 배포용 브랜치
- 기능 개발은 `feature/기능명` 브랜치에서 해 주세요

예시: $git checkout -b feature/login

# Commit Message 규칙

## 커밋 타입
- feat: 새로운 기능 추가
- fix: 버그 수정
- docs: 문서 수정
- style: 포맷, 세미콜론 등 비즈니스 로직에 변경 없는 수정
- refactor: 코드 리팩토링
- test: 테스트 코드 추가
- chore: 빌드 업무 수정, 패키지 매니저 수정

## 커밋 메시지 형식
[타입] 변경사항 요약
- 변경사항1
- 변경사항2
- issue : #번호

예시: [feat] Add clock component for hobby page , [fix] Correct typo in about page
