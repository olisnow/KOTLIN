package training.androidkotlin.helloworld

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ---------------------------------------------------------------------------------
        // STRING
        // ---------------------------------------------------------------------------------

        // create retrofit client
        val retrofit = Retrofit.Builder()
                .baseUrl("http://httpbin.org")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        // generate service based on client
        val service = retrofit.create(HttpBinServiceString::class.java)

        // execute getUserAgent request and convert response to String
        val call = service.getUserAgent()
        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.i(TAG, "User agent response: ${response?.body()}")
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        // ---------------------------------------------------------------------------------
        // JSON
        // ---------------------------------------------------------------------------------
        val retrofitJson = Retrofit.Builder()
                .baseUrl("http://httpbin.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // generate service based on client
        val serviceJson = retrofitJson.create(HttpBinServiceJson::class.java)
        val callJson = serviceJson.getUserInfo()
        callJson.enqueue(object: Callback<GetData> {

            override fun onResponse(call: Call<GetData>?, response: Response<GetData>?) {
                val getData = response?.body()
                Log.i(TAG, "Received object: $getData")
            }

            override fun onFailure(call: Call<GetData>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}
