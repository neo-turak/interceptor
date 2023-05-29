package cn.nurasoft.interceptor

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cn.nurasoft.request.DefaultFormatPrinter
import cn.nurasoft.request.PrintLevel
import cn.nurasoft.request.RequestInterceptor
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author 努尔江
 * Created on: 2023/5/15
 * @project Interceptor
 * Description:
 **/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestInterceptor = RequestInterceptor.Builder()
            .setPrintLevel(PrintLevel.ALL)
            .setFormatPrinter(DefaultFormatPrinter())
            .build()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()


        val re = Retrofit.Builder()
            .baseUrl("https://www.ghxi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = re.create(ApiService::class.java)

        findViewById<Button>(R.id.btnRequest).setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    val response = service.getTokoto()
                    val tv = findViewById<TextView>(R.id.lvMain)
                    tv.text = response.data.content
                }.onFailure {
                    Log.e(this.javaClass.simpleName,it.stackTraceToString())
                }
            }
        }
    }
}