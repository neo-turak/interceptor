# interceptor

漂亮的OkHTTP网络请求打印日志。
A pretty log printer for the OkHttp.

### Dependency

Jitpack[![](https://jitpack.io/v/neo-turak/interceptor.svg)](https://jitpack.io/#neo-turak/interceptor)

please add the Jitpack repository first.
Inside the settings.gradle

~~~groovy
//jitpack
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
~~~

then add the dependency
usually inside build.gradle file under app Module.

~~~groovy
//dependency
dependencies {
    //please replace the tag with the version code above.
    implementation 'com.github.neo-turak:interceptor:tag'
}
~~~

### Use

~~~kotlin
//Declare the RequestInterceptor.
val requestInterceptor = RequestInterceptor.Builder()
    .setPrintLevel(PrintLevel.RESPONSE)
    .setFormatPrinter(DefaultFormatPrinter())
    .build()
//add to okHttp interceptor
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()
 ~~~

### Advanced

or you can implement your own LogPrinter.
just implement to FormatPrinter then you are ready to go.

## Result

┌────── Response ──────────────────────────────────
│
| URL: http://api.txapi.cn/v1/hitokoto                            
│                                                                                           
│ /v1/hitokoto - is success : true - Received in: 70ms                                      
│                                                                                           
│ Status Code: 200 / OK                                                                     
│                                                                                           
│ Headers:                                                                                  
│ ┌ Server: nginx/1.14.0 (Ubuntu)                                                           
│ ├ Date: Mon, 15 May 2023 04:20:15 GMT                                                     
│ ├ Content-Type: application/json; charset=utf-8                                           
│ ├ Content-Length: 200                                                                     
│ ├ Connection: keep-alive                                                                  
│ └ Access-Control-Allow-Origin: *
│                                                                                           
│ Body:                                                                                     
│ {                                                                                         
│     "code": 200,                                                                          
│     "msg": "OK",                                                                          
│     "data": {                                                                             
│         "content": "你现在的气质里，藏着你走过的路，读过的书和爱过的人。",                                          
│         "type": "h",                                                                      
│         "from": "卡萨布兰卡",                                                                  
│         "creator": "小透明"                                                                  
│ },                                                                                    
│     "time": 1684124415                                                                    
│ }                                                                                         
└────────────────────────────────────────────

### have fun and enjoy.