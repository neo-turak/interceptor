# interceptor

漂亮的OkHTTP网络请求打印日志。

### 使用方法

~~~kotlin
//定义对象
        val requestInterceptor = RequestInterceptor.Builder()
            .setPrintLevel(PrintLevel.RESPONSE)
            .setFormatPrinter(DefaultFormatPrinter())
            .build()
//添加到okHttp构造内
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()
 ~~~

## 效果

┌────── Response ───────────────────────────────────────────────────────────────────────
│ URL: http://api.txapi.cn/v1/hitokoto?token=FtTtwONEZmM                                    
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
│     },                                                                                    
│     "time": 1684124415                                                                    
│ }                                                                                         
└─────────────────────────────────────────────────────────────────────────────────────── 