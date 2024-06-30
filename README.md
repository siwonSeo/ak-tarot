## 개발환경
- Java 21
- spring boot (3.3.0)
- JPA
- Gradle
- H2
- Swagger

# AK Tarot
- 타로 카드별 키워드 소개
- 카드 뽑는 방법별 해설 소개
- 주제, 카드(장 수), 역방향 포함 여부,자동/수동 선택후 타로 해설을 제공합니다.

## 사용 기술
<b>BE</b>
- Java 21
- Spring Boot
- Spring Data JPA
- Lombok

<b>FE</b>
- HTML5/CSS
- JavaScript
- Thymeleaf

<b>Build Tool</b>
- Gradle

<b>DB</b>
- H2

## 구현 절차
타로 카드(가장 보편적인 웨이트 타로)는 총 78장으로 구성되있으며 아래와 같이 구성되있습니다.
메이저 아르카나(22장)
마이나 아르카나(56장)
- 완드(14장)
- 컵(14장)
- 소드(14장)
- 펜타클(14장)

타로카드 해석에는 정방향/역방향에 따라 해석이 달라지므로 이 부분이 포함되있습니다.
-테이블 구성
카테고리(질문용)
타로카드
    카드 키워드
        카드 키워드별 해석

리딩 방식(타로카드 선택 장 수 별)
  선택 위치별 해설키워드

## 개발목적
- 추후 저만의 플랫폼으로 확대하고 싶은 목적 및 기술스택 확장 적용.
- 부담없이, 또는 시간이 부족한 사람들한테 간단한 타로점을 제공하기 위한 목적. (기존 플랫폼은 디테일하지않거나 유료화를 요구하는 버전이 대다수 인점에 착안)