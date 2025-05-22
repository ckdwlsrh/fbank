# 💰 fbank - CLI 기반 은행 계좌 관리 시스템

`fbank`는 간단한 은행 시스템을 CLI 환경에서 구현한 프로젝트입니다.  
JDBC를 사용하여 실제 데이터베이스와 연동되며, 계좌 관리 및 거래 내역 확인 기능 등을 제공합니다.

---

## 🚀 주요 기능

- ✅ 사용자 회원가입 / 로그인
- ✅ 계좌 생성 및 조회
- ✅ 입금 / 출금 / 이체
- ✅ 카드 조회
- ✅ 거래 내역 조회
- ✅ 계좌별 잔액 확인

---

## 🛠️ 기술 스택

| 구성 요소 | 사용 기술 |
|-----------|-----------|
| 언어 | Java 17 |
| DB 연동 | JDBC |
| 데이터베이스 | MySQL |
| CLI | System.in / System.out 기반 콘솔 앱 |
| 빌드 도구 | Gradle |

---

## 🗂️ 프로젝트 구조

```plaintext
fbank/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/fbankteam/fbank/
│   │   │       ├── util/           # JDBC 컨트롤
│   │   │       ├── domain/         # VO 클래스들
│   │   │       ├── dao/            # DB 접근 DAO 클래스들
│   │   │       ├── service/        # 비즈니스 로직
│   │   │       └── ui/             # CLI 입출력
│   │   └── resources/
│   │       └── application.properties
└── build.gradle
```

---

## 🤝 Contributing

[📄 컨트리뷰팅 가이드 보러 가기](./.github/CONTRIBUTING.md)
