package osama.me.namegamekotlin

import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NameGameService {
    @GET("profiles")
    fun profiles(): Flowable<List<Person>>

    companion object {
        fun create(): NameGameService
                = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().
                        addInterceptor(HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl("https://www.willowtreeapps.com/api/v1.0/")
                .build()
                .create(NameGameService::class.java)

    }


}