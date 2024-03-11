# MVC 패턴을 연습하기 위한 쇼핑몰 사이트 만들기
> 프로그래밍 연습을 위한 미니 프로젝트입니다.
> 
> 기본적인 구현이 완성된 후 지속적으로 리팩토링을 할 예정입니다.
> - 시작 기간 : 2024년 2월 13일 화요일
> - 마무리 기간 : 미정

## 사이트 설명
### 1. 사용 기술
> Spring 5.2.5
>
> junit 4.13.2
>
> mybatis 3.4.6
>
> log4j 1.2.17
>
> jstl 1.2.1

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
#### 1) 각 유저마다 가지고 있는 Credit 만들기 : 돈과 같은 개념
- [ ] DB User에 Credit을 추가한다. 유저는 이 Credit을 상시로 확인할 수 있다. 관리자 또한 유저 정보에서 확인 가능하다.
- [ ] 상품을 구매할 때, 이 Credit을 소모해서 구입한다. 이때, Credit이 부족하다면 구입할 수 없다고 alert을 제시해야한다.
- [ ] 일정한 행동을 할 시, Credit을 얻을 수 있다. (기획 중)

#### 2) 장바구니 추가
- [ ] 관리자가 아닌 유저들만 사용할 수 있다. 장바구니에 넣은 다음 한 번에 구매가 가능하다.
  - [ ] 이때, Credit 계산도 선행되야 한다.
- [ ] 유저별로 저장이 되므로 로그아웃 시에도 계속 남아있어야 한다.
- [ ] 각 제품별로 "장바구니 추가" 버튼을 추가하고, 이를 누르면 상호작용할 수 있도로 한다.

#### 3) **아리스 올리기(중대 사항)**
- [ ] 화면 우측 하단에 계속 존재한다.
- [ ] 쓰다듬으면 반응도 해주도록 한다.
- [ ] 유저의 각 반응에 대하여 안내 및 반응을 한다.

#### 4) UI 너무 구림
- [ ] 좀 이쁘게 바꿔다오...도트 그래픽 사용해도 될 거 같으니 참고
      
## 주의 사항
1. 각각 환경에 맞는 JDBC Connector가 필요하다

><예시>
>
>Oracle 21c XE : ojdbc.7 사용, Oracle 10c XE : ojdbc.14 사용
>
>여기서는 ojdbc.14를 사용하고 .gitignore로 설정한다.

## Refactoring 내역
### 240219 - 240220 코드 최적화
- [x] 페이지를 눌러도 검색 조건을 유지할 수 있도록 하기
- [x] 페이지 전체가 뜨던 상황을 다시 바꾸기
- [x] 페이지를 가져오는 것에 대한 효율적인 쿼리문 변경 : userDAO의 makeCurrentPages() 확인
- [x] UserSerivce의 return값을 Interface로 변경
- [x] VO 관련 패키지를 domain으로 변경
- [x] Util Bean으로 null exception 대비


### 240221 Advanced Tech 사용
User, Product, Purchase 부분의 view를 EL, JSTL로 변경한다.
- [x] User 변경
- [x] Product 변경
- [x] Purchase 변경

### 240222 - 논리상 맞지 않은 내용 수정
- 각 역할에 따른 UI 개선
  - [x] End User는 제품 번호, 등록일을 알 필요 없다. End User에게 필요한 내용만 걸러내도록 하자.
  - [x] End User는 사진 별로 제품을 보여준다.
  - [x] Admin에게는 더 다양한 정보를 제공할 수 있게 하고.

- 알고리즘 개선
- [x] 상품 검색 시 제품명의 일부만 맞아도 검색 결과에 출력될 수 있도록 한다.
- [x] 상품 검색 시 가격의 범위를 지정해서 검색 결과에 출력될 수 있도록 한다.
- [x] 상품별 정렬 추가 : User와 Manager의 차이를 두자

- 같은 상품에 대한 개수 추가
- [ ] 같은 상품에 대한 개수도 추가해서 만약 상품이 전부 팔린다면 매진으로 출력한다.
- [ ] 즉, 배송 상태는 각 유저의 구매 목록에서 확인할 수 있어야 한다.
- [ ] 상품이 주문 완료 됐을 때, 관리자가 임의로 배송 상태를 조절할 수 있어야 한다.
- [ ] 판매 완료 시, 상품 정보를 수정할 수 없어야 한다.
- [ ] 배송 중일 때, 구매할 수 없어야 한다.
- [ ] 배송 완료일 때, 모든 수정이 불가능해야 한다.

### 240302 - Spring, Mybatis를 이용한 Persistence Layer 간략화
- [x] User Component
- [x] Product Component
- [x] Purchase Component

### 240308 - Controller 최적화 및 Model과의 연결
- [ ] User Component
- [ ] Product Component
- [ ] Purchase Component 