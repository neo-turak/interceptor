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

2023-05-15 12:20:13.850 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │
URL: http://api.txapi.cn/v1/hitokoto
2023-05-15 12:20:13.850 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │
2023-05-15 12:20:13.851 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │ /v1/hitokoto - is
success : true - Received in: 70ms
2023-05-15 12:20:13.851 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │
2023-05-15 12:20:13.851 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │ Status Code: 200 /
OK
2023-05-15 12:20:13.851 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │
2023-05-15 12:20:13.851 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │ Headers:
2023-05-15 12:20:13.851 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │ ┌ Server:
nginx/1.14.0 (Ubuntu)
2023-05-15 12:20:13.851 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │ ├ Date: Mon, 15
May 2023 04:20:15 GMT
2023-05-15 12:20:13.851 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │ ├ Content-Type:
application/json; charset=utf-8
2023-05-15 12:20:13.851 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │ ├ Content-Length:
200
2023-05-15 12:20:13.851 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │ ├ Connection:
keep-alive
2023-05-15 12:20:13.851 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │ └
Access-Control-Allow-Origin: *
2023-05-15 12:20:13.852 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │
2023-05-15 12:20:13.852 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │ Body:
2023-05-15 12:20:13.852 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │ {
2023-05-15 12:20:13.852 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │     "code": 200,
2023-05-15 12:20:13.852 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │     "msg": "OK",
2023-05-15 12:20:13.852 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │     "data": {
2023-05-15 12:20:13.852 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │         "
content": "你现在的气质里，藏着你走过的路，读过的书和爱过的人。",
2023-05-15 12:20:13.852 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │         "type": "
h",
2023-05-15 12:20:13.852 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │         "from": "
卡萨布兰卡",
2023-05-15 12:20:13.852 3789-3890 -A-HttpLog-Response cn.nurasoft.interceptor D │         "
creator": "小透明"
2023-05-15 12:20:13.852 3789-3890 -R-HttpLog-Response cn.nurasoft.interceptor D │ },
2023-05-15 12:20:13.852 3789-3890 -M-HttpLog-Response cn.nurasoft.interceptor D │     "time":
1684124415
2023-05-15 12:20:13.852 3789-3890 -S-HttpLog-Response cn.nurasoft.interceptor D │ }
2023-05-15 12:20:13.852 3789-3890 HttpLog-Response cn.nurasoft.interceptor D
└───────────────────────────────────────────────────────────────────────────────────────