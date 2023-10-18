#  프리온보딩 백엔드 인턴십 선발과제(2023.10)
JAVA & Spring Boot

### 1. 요구사항 분석
유저, 회사, 지원은 등록(POST)만,  
공고는 조회(GET), 등록(POST), 수정(PUT), 삭제(DELETE)  
기능을 개발하라고 분석했습니다.

지원의 경우 중복 등록을 방지하라고 적혀있었기에  
유저 정보와 공고 정보를 복합키로 설정하고  
이를 unique key나 primary key로 설정하면  
데이터베이스에 중복 등록을 피할 수 있겠구나 생각했습니다.
### 2. 개발 일정 계획
    데이터베이스 모델링 -> 사용자 및 회사 등록 API -> 공고 API -> 지원 API
    검색 기능 및 상세 공고 반환 값에 해당 회사 공고 리스트 추가 및 지원은
    선택 사항이기에 필수 사항을 완성한 후 개발하기로 계획했습니다.
    코드 유닛 테스트 개발은 마지막으로 확인할 수 있게 최종으로 미뤘습니다.
    (개발 중에는 Postman으로 엔드포인트 테스트)
    * Postman을 사용하면 API 명세서도 같이 만들 수 있어 이점이 있다고 생각해서 사용했습니다.
    * Unit Test 기능은 업데이트 기능 테스트시 값 비교 함수를 사용할 수 있어 꼭 필요했던 것 같습니다.
### 3. 데이터베이스 모델링  
MariaDB를 DB로,  
Datagrip을 사용해  
시각적으로 확인하면서 모델링했습니다.   
![모델링 도식](http://cdn-0.plantuml.com/plantuml/png/RP7FIiD04CRl-nIVzLB1WX5wCAS-WrVHCYO36Tm_o-v43EAxQxgrBhBaDfFVpE-RFMIbfBEpvaedTeneIRpH4A41hq4rE8oIU509tkGtyP4IELZnh4jaXAHJC6Qmb3CUUKYpA3uDW26uI7uvojX3l6wxxgOkUtBSuvtIC57Qtj-TcZ5aMJVzDRhzXMAVgJIhSJzArbQ_bpaZAudDFPG_T2tK4bzdMuhHBiLrpfp-YwOIvjX2po4At4pIejOjBQHh8URtcDWzNUyVrk_sz3hFknTS17figZlBk34b5PfOCG1Mh4Gkp87zE3lx3G00)
### 4. API 명세서
[Postman](https://documenter.getpostman.com/view/14235470/2s9YR84tFz)
### 5.고민했던 사항
1) 요청 성공 시 반환 값을 JSON으로 제공해야하는 경우를 생각하면  
Entity 객체로 반환하는게 편리하고 오류가 없고 개발 속도도 빨랐으나,  
204, 400, 404, 500에러 같은 경우 body가 내용이 없을 수도(204)  
문자열로 반환해야하는 경우(400, 404, 500)이 있었습니다.
2) 이를 위해 status code(200, 201)와 body값을 받아 JSON타입으로  
응답을 반환하는 메서드를 만들었습니다.(CustomResponseEntity)
3) body 값을 생성할 때 3가지 방법을 찾았습니다.
   1) 해당 반환 값 규격에 맞는 DTO 클래스 생성 및 새로운 toString 메서드 정의 후 반환
   2) Entity를 Hash의 key <-> value로 변환 후 toStrig을 이용해 반환
   3) JSON 규격에 맞춘 문자열 반환
4) 공간 복잡도를 고려하여 JSON 규격에 맞춘 문자열을 반환했습니다.
5) 이 방법의 문제는 문자열에 오류가 없어야하기에 디버깅을 자주 꼼꼼히 해야했습니다.
6) 돌이켜보면 Hash를 사용하는 방법이 오류도 적고 생산도면에서 높았던 것 같습니다.
### 6. 문제점
지원 기능을 개발할 때 유저 정보(user_id)와 공고 정보(recruit_id)를  
복합키 및 primary key로 지정해 사용했습니다.  
중복된 값이 입력되었을 때 데이터베이스 상에선 오류가 발생하지만,  
Spring Boot 앱에선 이를 try catch 문에서 잡지 못하고 있습니다.  
중복 지원에 대한 응답을 위해 요청에 있는 값을 사용해  
데이터베이스에서 검색해 확인하는 방법을 사용 중입니다.  
Entity 복합키 설정에 대한 이해를 정확히 하지 못했기에 발생한 문제라고 생각합니다.  
기능이 작동하지 않는 중대한 문제는 아닌지라 일단 넘어갔으나,  
가독성과 성능을 위해서 수정하는 것이 좋을 것 같습니다.