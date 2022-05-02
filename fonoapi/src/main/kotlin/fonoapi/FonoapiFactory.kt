package fonoapi

import com.sushencev.devicebooking.service.devicedata.fonoapi.FonoApiService
import com.sushencev.devicebooking.service.devicedata.fonoapi.URL_BASE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object FonoapiFactory {

    fun create(okHttpClient: OkHttpClient? = null): FonoApiService {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(JacksonConverterFactory.create())

        okHttpClient?.let {
            retrofitBuilder.client(okHttpClient)
        }

        return retrofitBuilder.build().create(FonoApiService::class.java)
    }

}
