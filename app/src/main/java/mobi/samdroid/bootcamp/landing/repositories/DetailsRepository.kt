package mobi.samdroid.bootcamp.landing.repositories

import com.google.gson.JsonObject
import mobi.samdroid.bootcamp.base.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Response

class DetailsRepository {
    fun getDescription(onFinish:(isSuccess: Boolean, description: String?) -> Unit) {
        val call = ApiClient.apiService.getDescription("2")

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val description = response.body()?.get("text")?.asString
                    onFinish(true, description)
                } else {
                    onFinish(false, null)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                onFinish(false, null)
            }
        })
    }
}