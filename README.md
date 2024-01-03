# 💻 KotlinFolio 
<p align="center"><img src='./app/src/main/res/drawable/logo.png' width="30%"></p>

### 🚀 슬로건 

***

***


## 👩‍💻👨‍💻 개발 팀원 

- **[김성아](https://github.com/kimseongah)** : 한양대학교 컴퓨터소프트웨어학부 20학번
- **[김기현](https://github.com/surface03)** : KAIST 전산학부 22학번

## ⚙️ 개발 환경 
- Language: Kotlin
- OS: Android

```
minSdkVersion 26
targetSdkVersion 34
```

- IDE: Android Studio
- Target Decive: Galaxy S7


## ℹ️ 애플리케이션 설명 
- 3개의 탭으로 이루어진 애플리케이션입니다.
- 나만의 연락처, 갤러리를 추가하고 수정할 수 있습니다.
- 연락처, 갤러리를 달력에 연동할 수 있습니다.


#### Tab 1
- Tab1은 연락처를 저장합니다. 연락처를 저장할 때 그 사람에 대한 정보를 저장할 수 있는 나만의 연락처를 가질 수 있습니다.
    - **기존 데이터 불러오기**
        
        <img src='./images/contact/contact_main.png' width="30%">

    - **연락처에 대한 정보 저장**

        <img src='./images/contact/data1.png' width="30%">
        <img src='./images/contact/data2.png' width="30%">
        <img src='./images/contact/data3.png' width="30%">
    
    - **연락처 추가**
    
        <img src='./images/contact/contact_add.png' width="30%">
        <img src='./images/contact/contact_add_result.png' width="30%">

    - **연락처 데이터 수정**
    
        <img src='./images/contact/edit1.png' width="30%">
        <img src='./images/contact/edit2.png' width="30%">
        <img src='./images/contact/edit3.png' width="30%">

    - **연락처 사진 수정**

        <img src='./images/contact/edit4.png' width="30%">
        <img src='./images/contact/edit6.png' width="30%">
        <img src='./images/contact/edit5.png' width="30%">
    
    - **연락처 삭제**
    
        <img src='./images/contact/delete1.png' width="30%">
        <img src='./images/contact/delete2.png' width="30%">
        <img src='./images/contact/delete3.png' width="30%">

    - **앱 종료 후에도 데이터 유지**
      - 앱을 종료한 후에도 연락처 데이터가 유지됩니다.

        
#### Tab 2
- tab2의 주제는 **나만의 갤러리** 입니다. 
  - **메인 화면**
        <img src='./images/gallery/gallery_main.jpg' width="30%">
- 사진과 사진의 정보를 저장할 수 있습니다. 
- 저장 가능한 사진의 정보는 사진 제목, 사진 설명, 사진을 추가한 날짜입니다. 
- 사진을 짧게 누르면 사진을 확대할 수 있고, 길게 누르면 사진의 정보를 수정 및 삭제할 수 있습니다.
  - **저장된 정보**

    <img src='./images/gallery/show_info_gallery.jpg' width="30%">
  
  - **사진 확대**

    <img src='./images/gallery/expand_image_gallery.jpg' width="30%">
    
  - **정보 수정**

    <img src='./images/gallery/edit_info_gallery.jpg' width="30%">
    <img src='./images/gallery/edit_date_gallery.jpg' width="30%">
  
  - **사진 삭제**

    <img src='./images/gallery/delete_gallery.jpg' width="30%">


- 사용자로부터 디바이스 파일 접근 권한을 요청합니다. 사용자가 수락하면, 사용자의 핸드폰에 저장된 사진을 선택할 수 있는 창이 뜨게 되고, 선택한 사진을 갤러리에 추가할 수 있습니다.
  - **접근 권한 요청**

    <img src='./images/gallery/access_permission_gallery.jpg' width="30%">

  - **사진 추가**

    <img src='./images/gallery/added_image_gallery.jpg' width="30%">

  - **앱 종료 후에도 데이터 유지**
    - 앱을 종료한 후에도 연락처 데이터가 유지됩니다.

#### Tab 3
- Tab3는 Tab1과 Tab2에서 저장한 메모와 사진을 날짜 별로 볼 수 있는 캘린더입니다. 날짜를 클릭하면 사용자가 저장한 정보들을 한 눈에 볼 수 있습니다.

    - **연락처에 대한 정보 저장**
        
        날짜를 클릭하면 해당 날짜에 저장한 정보 또는 수정한 정보가 나옵니다.

        <img src='./images/calendar/view.png' width="30%">
        <img src='./images/calendar/1:1.png' width="30%">
        <img src='./images/calendar/1:3.png' width="30%">
       

## 🛠️ 사용된 기술 

#### Tab 1
- **기존 데이터 불러오기**
    
    - assets 폴더에 data.json을 저장하여 초기 데이터 구축합니다.
    - Gson 모듈로 json 파일을 `Person` 클래스 리스트로 저장합니다.

- **연락처에 대한 정보 저장**
    
    - 이름, 전화번호, 메모, 저장한 날짜를 `Person` 클래스의 attribute로 저장하고 dialog를 통해 보여줍니다.
    - 해당 dialog에서 연락처를 삭제, 수정할 수 있습니다.

- **연락처 데이터 수정**
    
    - 메모를 수정할 수 있습니다.
    - 메모를 수정하면 수정한 날짜가 오늘 날짜로 바뀝니다.
    - dialog에서 `editTextData.text.toString()`를 이용해서 원래 저장돼있던 메모가 뜨고 해당 텍스트를 변경할 수 있습니다.

- **연락처 사진 수정**

    - 연락처 수정 dialog에서 프로필 사진 수정 버튼을 누르면 갤러리에서 사진을 가져올 수 있습니다.
    - 갤러리를 연동하는 것은 해당 dialog가 아닌 `ContactFragment`에서 수행하여 `Listener`를 이용했습니다.
    - dialog를 띄운 adapter에서 선택한 연락처의 position을 받아와서 `SharedPrefereces`를 이용해 공유할 수 있습니다.

- **연락처 삭제**

    - 연락처 삭제도 dialog를 띄워서 진행합니다.
    - 삭제하는 것이 맞는지 한 번 더 확인한 후 삭제할 수 있습니다.

- **앱 종료 후에도 데이터 유지**

    - 안드로이드에서 제공하는 `SharedPreferences`를 사용하여 앱이 종료된 후에도 연락처 데이터가 유지되도록 구현했습니다.
    - 이름, 전화번호, 날짜 등 연락처와 관련된 정보를 `Int`와 `String`으로 변환하여 `SharedPreferences`에 저장했습니다.
    - 앱을 최초로 실행하는 경우, data.json으로부터 연락처 정보를 가져옵니다. 그렇지 않은 경우, `SharedPreferences`에 저장된 연락처 정보를 가져옵니다.
    
#### Tab 2
- **기존 데이터 불러오기**
  - drawable 폴더에 예시 이미지 4개를 저장했습니다.
  - drawable 폴더로부터 예시 이미지 정보를 불러와 `GalleryImage` 클래스로 정보를 저장합니다.
- **이미지 데이터 정보 저장**
  - `GalleryImage` 클래스는 이미지의 이름, 설명, 이미지 데이터, 저장한 날짜에 대한 정보를 담고 있습니다.
  - `GalleryImage` 클래스를 `MutableList`로 관리하는 `RecyclerGalleryImageAdapter` 클래스를 이용해 이미지들을 `RecyclerView`로 띄웁니다.
  - `GalleryImage` 정보가 수정되면 `MutableList` 또한 수정되고, `RecyclerGalleryImageAdapter` 클래스가 수정 정보를 받아 `RecyclerView`를 수정합니다.
  - `GridLayoutManager`를 이용해 이미지를 열이 2개인 Grid로 보여줍니다.
- **이미지 확대**
  - 이미지를 짧게 누르면 activity를 호출합니다.
  - Intent를 통해 누른 이미지에 대한 정보를 전송하여 이미지를 크게 띄웁니다.
- **이미지 데이터 수정**
  - 이미지 제목, 설명, 날짜를 수정할 수 있습니다.
  - 이미지를 꾹 누르면 dialog를 띄우고, 이를 통해 유저가 입력한 데이터를 받아 `GalleryImage` 클래스의 정보들을 수정합니다.
- **이미지 삭제**
  - 이미지 삭제 또한 dialog를 띄워서 진행합니다.
  - 삭제하는 것이 맞는지 한 번 더 확인한 후 삭제할 수 있습니다.
- **이미지 추가**
  - `android.permission.READ_EXTERNAL_STORAGE`권한이 없는 경우, 유저에게 해당 권한을 요청하고 권한을 받아옵니다.
  - 사용자의 핸드폰에 저장된 사진을 선택할 수 있는 activity를 부르고, 사용자가 선택한 이미지에 대한 정보를 uri로 받아와 `GalleryImage` 클래스에 저장합니다.
- **앱 종료 후에도 데이터 유지**
  - 안드로이드에서 제공하는 `SharedPreferences`를 사용하여 앱이 종료된 후에도 이미지 데이터가 유지되도록 구현했습니다.
  - 이름, 설명, 날짜 등 이미지와 관련된 정보를 `Int`와 `String`으로 변환하여 `SharedPreferences`에 저장했습니다.
  - 앱을 최초로 실행하는 경우, drawable 폴더의 예시 이미지 4개를 가져옵니다. 그렇지 않은 경우, `SharedPreferences`에 저장된 이미지 정보를 가져옵니다.
#### Tab 3
- **Tab1, 2와 데이터 연동하기**

        class SharedViewModel : ViewModel() {
            val persons = MutableLiveData<List<Person>>()
            val images = MutableLiveData<List<GalleryImage>>()
        }
    - View Model을 이용해서 데이터 공유 및 변경 시에 업데이트가 가능합니다.
- **이미지와 메모 같이 보여주기**

    - Adapter에 ViewHolder를 넣어주고 이미지와 텍스트를 쌍으로 전달하여 바인딩합니다.
    - 둘 중 하나는 `null` 로 처리해서 하나씩 바인딩할 수 있습니다.

## 📲 다운로드 

- [APK 다운로드](https://drive.google.com/file/d/1Up816-krCkXXLpfbn8v9KhoE0R_MYDqb/view?usp=share_link)
