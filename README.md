# MVC 패턴을 연습하기 위한 쇼핑몰 사이트 만들기
> 프로그래밍 연습을 위한 미니 프로젝트입니다.
> 
> 기본적인 구현이 완성된 후 지속적으로 리팩토링을 할 예정입니다.
> - 시작 기간 : 2024년 2월 13일 화요일
> - 마무리 기간 : 미정

## 시나리오
### 1. 회원 관리
- [x] 구현
- [x] 코드 분석 및 이해

### 2. 상품 관리
- [x] 최근 본 상품 조회 (비회원, 회원, 관리자)
- [x] 상품 검색 (비회원, 회원, 관리자)
- [x] 상품 조회 (회원, 관리자)
- [x] 상품 상세 조회 (회원, 관리자)
- [x] 상품 등록 (관리자)
- [x] 상품 수정 (관리자)

### 3. 판매 관리
- [x] 구매 목록 조회(ListPurchaseAction)
     - [x] 구매에 대한 상세 정보를 요청해야 한다. (GetPurchaseAction)
     - [x] 구매 내역을 수정해야 한다.
- [x] 구매 (AddPurchaseAction)
     - [x] 구매 내역에 들어갈 때, 정보를 가지고 들어가야 한다. (AddPurchaseViewAction)
     - [x] 구매 상태 수정이 가능해야 한다. (UpdatePurchaseAction, UpdatePurchaseViewAction)
- [x] 판매 목록 조회 (ListSaleAction)
     - [x] 배송 상태를 변경할 수 있어야 한다. (UpdateTranCodeAction)

### 4. 요구사항 외 추가 기능

## 주의 사항
1. 각각 환경에 맞는 JDBC Connector가 필요하다

><예시>
>
>Oracle 21c XE : ojdbc.7 사용, Oracle 10c XE : ojdbc.14 사용
>
>여기서는 ojdbc.14를 사용하고 .gitignore로 설정한다.

## Refactoring 내역
1. 240219 - 240220 코드 최적화
- [x] 페이지를 눌러도 검색 조건을 유지할 수 있도록 하기
- [x] 페이지 전체가 뜨던 상황을 다시 바꾸기
- [x] 페이지를 가져오는 것에 대한 효율적인 쿼리문 변경 : userDAO의 makeCurrentPages() 확인
- [x] UserSerivce의 return값을 Interface로 변경
- [x] VO 관련 패키지를 domain으로 변경
- [x] Util Bean으로 null exception 대비
