# Github 검색기
Github OpenAPI를 사용한 Github 정보 검색 Android App 입니다.

## 1. Git-flow
- main<br>
기준이 되는 브랜치로 앱 배포하는 브랜치 입니다.
- dev<br>
개발 브랜치로 개발자들이 이 브랜치를 기준으로 각자 작업한 기능들을 합칩니다.
- feature-[name]<br>
단위 기능을 개발하는 브랜치로 기능 개발이 완료되면 dev 브랜치에 합칩니다.
- hotfix-[name]<br>
main 브랜치에서 발견한 버그를 수정하기 위해 사용합니다.


## 2. OpenAPI
Github에서 제공하는 OpenAPI를 사용했습니다.<br>
>https://docs.github.com/en/rest

## 3. Tech
`Android` `Kotlin` `MVVM` `AAC ViewModel` `AAC LiveData` `Coroutine` `RxJava3` `DataBinding` `RepositoryPattern` `Dagger2` `Retrofit2` `OkHttpClient` `Android-KTX` `Paging3` `Glide` `Lint` `JUnit` `Mockito` `Espresso`

## 4. Setup
이 설정 과정은 진행하지 않아도 정상적으로 이용할 수 있지만, GithubAPI의 속도 제한과 요청 제한 때문에 원활하게 이용하지 못할 수 있습니다. 제한 없이 사용하기 위해서는 이 설정 과정을 따라야 합니다.
1. Github 우측 프로필 > `Settings` > 좌측 메뉴의 `Developer Settings` > `Personal access tokens` > `Generate new token` > `repo` 권한과 `user` 권한 선택 후 발급받습니다.
2. `/app/src/main/java/com/smparkworld/githubsearcher/di/NetworkModule.kt` 파일을 엽니다.
3. `NetworkModule.kt`파일 18번째 줄의 token 상수에 발급받은 token을 입력합니다.

## 5. Preview
1. 아이디 검색
 <img src="https://user-images.githubusercontent.com/47319426/133830269-5ab45ec9-b779-4696-9229-0bec3f5a039c.jpg" width="300">

2. 아이디 검색 결과1
 <img src="https://user-images.githubusercontent.com/47319426/133830290-83786936-8906-40fc-b2a8-19e270a6439a.jpg" width="300">
 
3. 아이디 검색 결과2
 <img src="https://user-images.githubusercontent.com/47319426/133830296-6b56bf89-56f5-4efb-b4e8-3731277b89d9.jpg" width="300">

4. 사용자 상세
 <img src="https://user-images.githubusercontent.com/47319426/133830300-8f45319b-2036-4e10-b4f4-44dc2057650d.jpg" width="300">
 
