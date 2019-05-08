# 개발 동기 및 목표
<ul>
  <li>음악 파일은 많이 가지고 있지만, 관리에 어려움이 발생</li>
  <li>이미지 중복 체크 프로그램 및 Mp3 파일에 포함된 태그를 이용하여 중복 체크하는 프로그램은 있지만 음원을 비교하는 프로그램을 찾지 못해 개발을 하기로 마음 먹음</li>
  <li>푸리에 변환을 사용하여 파형을 분석하는 알고리즘을 찾아 해당 알고리즘 사용하여 개발</li>
  <li>멀티 쓰레딩 기술을 사용하여 작업속도 단축 및 유휴 CPU 사용 능력 향상</li>
</ul>

# 사용 라이브러리
<ul>
  <li>mp3spi</li>
  <li>jlayer</li>
  <li>musicg</li>
  <li>mp3agic</li>
  <li>junit</li>
</ul>

# 초기 레이아웃 설정
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTk3/MDAxNTU3Mjk0NzM3MzI0.xcSKgeNnfemXv7w8L4FRV5XFAxNbkBE755-uaqrt41og.pxYl1gTSx4xLIBFLwRRcTo4Y88gHCA5F7_y83v3xj9Mg.PNG.younggu1545/%EA%B7%B8%EB%A6%BC28.png?type=w966"/>
<ul>
  <li>비교 프로그램(좌측) - 이미지 중복 체크 프로그램</li>
  <li>계획한 레이아웃(우측)</li>
</ul>

# 화면 설명
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTA1/MDAxNTU3Mjk1ODgzMDk1.QkoEWYGqPUn9cU7ywbIgfIYxr9A0IJvEX6fhNncfGPYg.RV1c4Lz4JMXayJFf-7Lmk9ZuoyigaXG9VNs3d5IUeqUg.PNG.younggu1545/%EA%B7%B8%EB%A6%BC37.png?type=w966"/>
<ol>
  <li>Map(Key, List)를 활용하여 중복된 음원 파일로 검사된 파일들을 그룹별로 표시</li>
  <li>Tree 및 FileSystemView를 활용하여 현재 컴퓨터의 파일 트리 출력</li>
  <li>TreeSelectionListener를 활용하여 선택한 폴더 받아오며, JTable을 활용하여 리스트 출력 및 필요 시 선택한 폴더 삭제 기능 구현</li>
  <li>JProgressBar를 사용하여 작업 진행내용이 표시<br>
      멀티 쓰레드를 사용하여 사용자가 설정한 쓰레드 개수(⑤)를 활용하여 작업 진행</li>
  <li>JTabbedPane을 사용하여 하나의 공간에서 옵션 선택할 수 있게 진행.<br>
      JSlider를 사용하여 각각의 옵션 쉽게 변경할 수 있도록 설정</li>
  <li>JTextFiled를 사용하여 현재 진행사항 표시(로그)</li>
  <li>①에서 선택한 파일 위치 표시</li>
</ol>

# 동작 알고리즘
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTQ1/MDAxNTU3Mjk0NzM3Mjk0.8RiUMVB5aMGguuoQrhO2XgPA08R5Z7FJCrC5CSkQMPUg.oKXD2jSFhgNKe2y6py5hesDMfKnFJUtjfXF1IEaneywg.PNG.younggu1545/%EA%B7%B8%EB%A6%BC29.png?type=w966"/>
<ol>
  <li>사용자가 작업을 원하는 파일이 들어있는 폴더 선택하여 작업 진행</li>
  <li>폴더 내 mp3 및 wav 파일만 찾아 리스트에 저장</li>
  <li>mp3파일의 경우 wav파일로 변환</li>
  <li>변한된 wav 파일의 포멧을 변환하고, 비교하는데 필요한 구간만큼 잘라서 저장</li>
  <li>생성된 파일의 비교 작업 진행</li>
  <li>작업 완료 후 작업 시 사용한 파일 삭제</li>
  <li>검사 완료 된 리스트 출력</li>
</ol>
