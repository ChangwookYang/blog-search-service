# 블로그 검색 서비스

### Executable Jar 파일 구글 Drive URL 
: https://drive.google.com/file/d/1140YOSWStfrliWsqqLcrV5DMysEyicv0/view

### 실행문 
: java -jar blog-search.jar

### API 명세 (jar파일 실행 후 접속) 
: http://localhost:8080/swagger-ui.html  
---
[GET] /api/search 블로그 검색
<img width="1400" alt="image" src="https://user-images.githubusercontent.com/66955409/226924171-304d809a-dba1-41a1-abd4-51e064e647e8.png">

[GET] /api/popular 인기 검색어 목록
<img width="1400" alt="image" src="https://user-images.githubusercontent.com/66955409/226924833-e6aab23d-523a-4d92-a99a-bcff90b21d29.png">

---
### 스펙
  - JAVA 11
  - Spring Boot
  - Gradle 
  - 블로그 검색 Kakao, Naver API는 서버 연동
  - DB : H2, DB 컨트롤 : JPA
  - 외부 라이브러리 및 오픈소스 
   
[Redis]  
> DB 카운트 업데이트 횟수를 줄이기 위해 Redis 적용  
   
[Swagger UI 2.9.2] 
> API 문서 작성  

[Spring retry]
> 카카오 API 실패 시 Naver API 검색


---
### 기능 구현
1. [GET] /api/search 블로그 검색

* 키워드 통해서 블로그 검색 : Sorting(정확도순, 최신순), Pagination 형태 제공  
* 카카오 API 블로그 검색 (예외 발생 시 Naver API 블로그 검색)
* 검색 소스는 Factory pattern으로 구현하여 새로운 검색 소스 추가 가능


2. [GET] /api/popular 인기 검색어 목록

* 최대 10개 키워드 제공
* 검색된 횟수 함꼐 표기

3. 멀티 모듈 구성

* application : controller, service 등 서비스처리 로직
* common : 공통적으로 사용하는 domain, 검색 API service, repository

4. 트래픽 및 키워드 검색 횟수 정확도를 높이기 위한 방안

* Redis 추가(DB 트랜잭션을 줄이기 위함)
* 검색횟수 10번까지는 검색할때마다 DB 동기화  
* 검색횟수 10 ~ 100번까지는 5번 검색될때마다 DB 동기화  
* 검색횟수 100 ~ 1000번까지는 10번 검색될때마다 DB 동기화, 1000부터는 100번마다 DB 동기화 처리  
* Redis 연동이 비정상일 경우 검색할때마다 DB 동기화 처리
* 키워드 검색과 별도 트랜잭션으로 처리

5. KAKAO 검색 API 예외 발생 시 Naver API 검색

* Spring retry 활용하여 @Recover에서 Naver API 검색 호출

6. 테스트 케이스 작성
<img width="655" alt="image" src="https://user-images.githubusercontent.com/66955409/226931360-48c851da-c416-4cce-ae33-a5be4fe5e20d.png">

7. 예외 처리
* https://github.com/ChangwookYang/blog-search-service/commit/75f588bbb19954e1dc3d337f5695ed5fe8f965df

8. Github Project 관리
* Backlog : https://github.com/users/ChangwookYang/projects/5/views/1
* By priority : https://github.com/users/ChangwookYang/projects/5/views/2
* By size : https://github.com/users/ChangwookYang/projects/5/views/3
* 스프린트 : https://github.com/users/ChangwookYang/projects/5/views/5

9. ERD 및 use case 다이어그램 작성
* ERD : https://github.com/ChangwookYang/blog-search-service/blob/main/blog-search-erd.svg
* use case 다이어그램 : https://github.com/ChangwookYang/blog-search-service/blob/main/blog-search-service.svg


