package com.smparkworld.githubsearcher.di

import com.smparkworld.githubsearcher.BuildConfig
import com.smparkworld.githubsearcher.data.remote.api.GithubAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 적은 제한의 Github API를 호출하기 원하시면
    // User와 Repo 권한을 허가한 Personal Access Token을 입력해서 사용해주세요.
    private const val token  = ""
    private const val accept = "application/vnd.github.v3+json"

    private fun getClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().run{
                    if (token.isNotBlank()) {
                        addHeader(BuildConfig.AUTH, "token $token")
                    }
                    addHeader(BuildConfig.ACCEPT, accept)
                    build()
                }
            )
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().client(getClient())
            .baseUrl(BuildConfig.SERVER_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @JvmStatic
    fun provideGithubAPI(retrofit: Retrofit): GithubAPI = retrofit.create(GithubAPI::class.java)
}