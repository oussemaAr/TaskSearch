package tn.org.tasksearch.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.org.tasksearch.BuildConfig

class TaskClient {

    companion object {
        @Volatile
        private var instance: TaskService? = null

        fun getInstance(): TaskService {
            return instance ?: synchronized(this) {
                return instance ?: initRetrofit()
            }
        }

        private fun initRetrofit(): TaskService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(initHttpClient())
                .build()
                .create(TaskService::class.java)
        }

        private fun initHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = request.newBuilder()
                        .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(logging)
                .build()
        }
    }
}