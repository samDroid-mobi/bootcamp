package mobi.samdroid.bootcamp.base.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("loremipsum")
    fun getDescription(@Query("paragraphs") nb: String): Call<JsonObject> // generated URL https://api.api-ninjas.com/v1/loremipsum?paragraphs={nb})
}