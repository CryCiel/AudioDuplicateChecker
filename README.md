# 개발 동기
<ul>
  <li>음악 파일은 많이 가지고 있지만, 관리에 어려움이 발생</li>
  <li>이미지 중복 체크 프로그램 및 Mp3 파일에 포함된 태그를 이용하여 중복 체크하는 프로그램은 있지만 음원을 비교하는 프로그램을 찾지 못함</li>
<ul>

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
<img src="https://postfiles.pstatic.net/MjAxOTA1MDhfMTg2/MDAxNTU3Mjk0NzM3MzUy.LlFVVhXhQiFJAaIAmvSfQW7lY_0jI54TFV2129269Gkg.HI0CqsM6t9iCEdVX89lmlpZYUb1DprJ65LFSHwsdhLsg.PNG.younggu1545/%EA%B7%B8%EB%A6%BC27.png?type=w966"/>
<ol>
  <li>Map(Key, List)를 활용하여 중복된 음원 파일로 검사된 파일들을 그룹별로 표시</li>
  <li>Tree 및 FileSystemView를 활용하여 현재 컴퓨터의 파일 트리 출력</li>
  <li>TreeSelectionListener를 활용하여 선택한 폴더 받아오며, JTable을 활용하여 리스트 출력 및 필요 시 선택한 폴더 삭제 기능 구현</li>
  <li>JProgressBar를 사용하여 작업 진행내용이 표시<br>
      멀티 쓰레드를 사용하여 사용자가 설정한 쓰레드 개수(ⓔ)를 활용하여 작업 진행</li>
  <li>JTabbedPane을 사용하여 하나의 공간에서 옵션 선택할 수 있게 진행.<br>
      JSlider를 사용하여 각각의 옵션 쉽게 변경할 수 있도록 설정</li>
  <li>JTextFiled를 사용하여 현재 진행사항 표시(로그)</li>
  <li>1에서 선택한 파일 위치 표시</li>
</ol>
