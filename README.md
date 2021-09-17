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
`Android` `Kotlin` `MVVM` `AAC ViewModel` `AAC LiveData` `Coroutine` `DataBinding` `RepositoryPattern` `Dagger2` `Retrofit2` `OkHttpClient` `Android-KTX` `Paging3` `Glide`

## 4. Setup
이 설정 과정은 진행하지 않아도 정상적으로 이용할 수 있지만, GithubAPI의 속도 제한과 요청 제한 때문에 원활하게 이용하지 못할 수 있습니다. 제한 없이 사용하기 위해서는 이 설정 과정을 따라야 합니다.
1. Github 우측 프로필 > `Settings` > 좌측 메뉴의 `Developer Settings` > `Personal access tokens` > `Generate new token` > `repo` 권한과 `user` 권한 선택 후 발급받습니다.
2. `/app/src/main/java/com/smparkworld/githubsearcher/di/NetworkModule.kt` 파일을 엽니다.
3. `NetworkModule.kt`파일 18번째 줄의 token 상수에 발급받은 token을 입력합니다.
 
