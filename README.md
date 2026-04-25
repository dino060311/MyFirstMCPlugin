# 🎮 MyFirstMCPlugin - 마인크래프트 첫 플러그인 프로젝트

마인크래프트 서버 환경에서 동작하는 플러그인 개발의 기초 역량을 증명하기 위한 프로젝트입니다.
단순한 코드 작성을 넘어, 실제 서버 환경에서의 빌드 및 배포 전 과정을 완수했습니다.

---

## 🛠️ 개발 환경 및 기술 스택
- **Language:** Java 21 (LTS)
- **Build Tool:** Maven
- **API:** PaperMC API (최신 버전 호환)
- **IDE:** Visual Studio Code

---

## ✅ 주요 성과 및 확인 과정

### 1. Maven 빌드 성공 (Build Automation)
Java 21 환경에서 Maven을 사용하여 소스 코드를 실행 가능한 `.jar` 파일로 변환하는 빌드 프로세스를 수립했습니다. 터미널을 통해 프로젝트의 무결성을 검증하고 빌드 성공을 확인했습니다.

> **[빌드 결과 인증]**
> <img width="357" height="79" alt="스크린샷 2026-04-25 130507" src="https://github.com/user-attachments/assets/9638ac60-ca35-42ff-a4c6-2694fba771e7" />

### 2. 서버 내 플러그인 활성화 확인 (Plugin Load)
PaperMC 서버의 `plugins` 폴더에 배포 후, 서버 콘솔에서 `/pl` 명령어를 입력하여 플러그인이 정상적으로 로드(초록색 활성화)된 것을 최종 확인했습니다.

> **[플러그인 활성화 인증]**
> <img width="750" height="200" alt="스크린샷 2026-04-25 132333" src="https://github.com/user-attachments/assets/b4d21684-6168-4efa-a679-0fa30fd43434" />

---

## 💡 해결했던 문제 (Troubleshooting)
- **파일 시스템 잠금 이슈 해결:** 서버 구동 중 파일 덮어쓰기 제한 문제를 서버 프로세스 제어(`stop` 커맨드)를 통해 해결하며 배포 안정성을 확보했습니다.

---
*Last Updated: 2026-04-25*

