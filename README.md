# Pot, Folio  



#### 당신의 꿈은 어디에 저장되어 있나요? 

우리는 한 해에 많은 작업물들을 제작하고 있습니다.  
개발, 디자인, 사진, 헤어 등 수없이 많은 작품들이 
여러 사이트에  올라오는 것을 보고  저희는 이 멋진 작품들을 한 눈에 볼 수 없을까? 라는 물음표에서 시작한 Pot, Folio.

바구니라는 뜻을 가진 Pot에서 착안하여 
자신의 실력을 보여줄 수 있는 작품이나 관련 내용을 집약한 
사용자들의 포트폴리오를 Pot, Folio에 담고 간직하는 목적으로 앱을 제작하였습니다.

지금부터 당신의 꿈을 바구니에 담아주시길 바랍니다.  
https://youtu.be/9vj6aC8ljeA  

### 1) 앱 이름 설명
Pot, Folio는 Portfolio(포트폴리오)라는 단어로부터 만들어진 앱 이름이다.   
포트폴리오를 담을 수 있는 공간을 만들고자 하는 생각에서부터 시작하여 'Pot(단지, 냄비)이라는 곳에 포트폴리오를 넣자!'라는 의미로 앱 이름을 설정했다.

### 2) 기획 과정
- **목적** : 포트폴리오를 기록하는 앱을 제작하므로써 자신만의 포트폴리오를 기록하고자 하는 사용자와 그 분야에 관심이 있는 사용자가 원하는 바를 앱을 통해 만족시켜준다
- **기대효과** : 서로의 포트폴리오를 공유할 뿐만 아니라 다양한 사람들의 작품을 감상하며 그 분야의 지식을 얻고, 발전 가능한 환경을 조성한다
- **목표**
  + 자신만의 포트폴리오 구성하기  
  + 사용자가 관심있는 분야의 포트폴리오 감상하기   
  + 독특한 소재의 프로필로 자신을 알리기
- **메인 타겟**
  + 자신만의 포트폴리오를 기록하고 싶은 사용자
  + 관심 분야의 아이디어를 얻고 싶은 사용자
  
### 3) 메뉴 구조도
- 메인 화면 : 핫폴리오와 올폴리오로 구성하여 핫폴리오에서는 조회수가 많은 포트폴리오를 추천하고, 올폴리오에서는 모든 포트폴리오를 보여준다.
- 검색 화면 : 검색창에서 검색하고자 하는 내용을 찾을 수 있고, 화면의 중간 부분에는 명언을 배치하여 명언을 읽고 다시 동기부여가 될 수 있도록 한다. 아래부분엔 추천 검색서를 통해 볼 수 있는 포트폴리오를 추천하고 있다.
- 게시글 화면 : 여러개의 이미지를 추가하여 포트폴리오를 구성할 수 있고, 메모나 적고자 하는 내용을 넣어 게시글을 작성할 수 있다.
- 로그인 및 회원가입 화면 : 로그인과 회원가입을 할 수 있는 기본적인 화면 구성
- 마이페이지 : 자신을 나타낼 수 있는 명함으로 구성된 자기 소개 내용과 그동안 올렸던 포트폴리오를 볼 수 있는 마이폴리오 부분이 존재한다.

### 4) 시연 영상
시연 영상에서 더 자세하고 정확한 내용을 확인하실 수 있습니다.  
https://youtu.be/9vj6aC8ljeA

### 5) 시연 영상 세부 정보 정리
- 시작 화면 
  + GIF 삽입 : raw 파일에 저장해둔 제작한 gif 파일을 외부 라이브러리 Glide를 활용해 삽입
  + 갤러리 접근 : READ_EXTERNAL_STORAGE 권한 필요, 권한 거부 시 앱 사용이 제한
- 회원가입 : 회원가입시 groupTBL에 사용자 정보를 저장하여 이름, 아이디 중복 검사를 진행
- 로그인 화면
  + 로그인 기능 : groupTBL 데이터에서 원하는 값을 얻어내어 아이디와 비번 비교
  + 자동 로그인 : saveData(), loadData() 선언하여 로그인한 아이디와 비번을 저장하고 로드
  + 알림 기능 : notification을 이용해 사용자들에게 필요한 정보를 제공
- 메인 화면 
  + HotFolio : 다른 사람들의 포트폴리오를 조회수 순으로 나열함(다른 사람들의 데이터가 필요한 부분)
  + AllFolio : SlidingUpPanel을 활용해 슬라이더 구현 및 포트폴리오 최신 업로드 순으로 나열(다른 사람들의 데이터가 필요한 부분)
- 검색 화면 
  + 감성 요소 : 랜덤 변수 생성을 통해 랜덤으로 감성 명언 출력
  + 키워드 검색 : 키워드를 통해 게시글을 검색함
  + 추천 검색어 : 사용자 맞춤 검색어를 추천함
- 게시글 추가 화면
  + 게시글 업로드 시 조건 : 업로드시 이미지는 필수 업로드 사항이며 제목과 설명 미입력 시 기본 문구가 삽입됨.(조건 미충족 시 Toast 메세지를 이용해 알림)
  + 이미지 : 다중 선택된 이미지들은 CLipData를 통해 이미지 uri를 얻어 String형식으로 ImageTBL에 저장함. 또한, 이미지 uri를 Bitmap으로 변환하여 listView에 이미지를 보임
  + 입력된 제목, 설명은 조건에 따라 textTBL에 저장
  + index를 통한 게시글 구분
  + 업로드 완료시, 해당 페이지에 남아 있는 데이터 초기화
- 게시글
  + 게시물 연결 : 마이페이지에서 선택된 그리드뷰의 postId를 받아와 해당 postId의 게시글 보이기
  + 게시물 이미지 : 게시글의 이미지는 recyclerview에 보여짐
- 마이페이지
  + 프로필, 사용자 명함 정보 구현 : CardTBL, ProTBL을 활용
  + 프로필 이미지 뷰 : ProTBL에서 가져온 이미지 uri를 Bitmap으로 변환하여 프로필 이미지 뷰에 보임
  + 게시글 미리보기 : 각 i_number로 구분되어 있는 ImageTBL의 이미지들 중 첫 이미지만 gridView에 보이도록 함
  + 각 gridView의 이미지 클릭 시 해당 이미지의 게시글로 이동
- 명함 수정
  + 데이터 업데이트 : CardTBL 생성을 통해 명함 정보 수정 시 Update되는 데이터를 저장함 -> 마이페이지에서 사용
  + 이미지 : 갤러리로부터 이미지의 uri를 받아 ProTBL에 저장
  + 수정 조건 : 마이페이지로부터 name을 받아와 name과 동일한 행이 존재하는 경우 수정이 가능하도록 함
- 명함 화면 : CardTBL로 부터 데이터를 가져와 마이페이지와 동일한 방식으로 텍스트에 데이터 적용
- 환경설정 
  + 이용약관 : 다이알로그를 생성해서 사용자가 이용약관을 쉽게 확인하도록 함
  + 비밀번호 변경 : groupTBL로부터 아이디와 비번을 얻어 동일한 테이블에 존재하는 아이디와 비번인 경우에 비번을 변경할 수 있도록 함


```
손쉽게 나만의 기록을 남길 수 있는 모바일 포트폴리오   
간편하게 자신을 알릴 수 있는 모바일 명함    
자기 PR이 중요한 시대에 중요한 역할을 할 수 있는 어플리케이션  
```


---------------------------------------------------------------------------------------
[개발자]   
서울여자대학교 디지털미디어학과 2020111236 김가현   
(mapledt001@naver.com)  
서울여자대학교 디지털미디어학과 2020111256 문선민   
(tjsalsdl0602@naver.com)  

[디자이너]  
서울여자대학교 디지털미디어학과 2020111271 송정연   
(songjjing@naver.com)   
서울여자대학교 디지털미디어학과 2020111281 이기은   
(lkeyun3301@naver.com)  


**서울여자대학교 프로그래밍 구루2 안드로이드 앱 - 디미랑이**  
**SWU Programming GURU2 Android App - DIMITIGER**












