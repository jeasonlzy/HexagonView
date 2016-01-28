# HexagonView
六边形带圆角的自定义View，支持图文混排，点击区域，水平垂直方向切换，圆角大小等各种属性

## 演示
 ![image](https://github.com/jeasonlzy0216/OkHttpUtils/blob/master/screenshots/preview.jpg)![image](https://github.com/jeasonlzy0216/OkHttpUtils/blob/master/screenshots/pre.gif)![image](https://github.com/jeasonlzy0216/OkHttpUtils/blob/master/screenshots/dm.gif)![image](https://github.com/jeasonlzy0216/OkHttpUtils/blob/master/screenshots/dm2.gif)![image](https://github.com/jeasonlzy0216/OkHttpUtils/blob/master/screenshots/upload.gif)


## 1.用法

使用前，对于Android Studio的用户，可以选择添加:
```java
    compile project(':library_okhttputils')
    compile project(':library_okhttpserver')
```
其中是我的另一个开源项目，完全仿微信的图片选择库，带有自带 矩形图片裁剪 和 圆形图片裁剪 功能，有需要的可以去下载使用，附上地址：[https://github.com/jeasonlzy0216/ImagePicker](https://github.com/jeasonlzy0216/ImagePicker)
```java
    compile project(':library_ImagePicker')
```
	
## 2.注意

`library_okhttputils`使用的okhttp的版本是最新的3.0版本，和以前的2.x的版本可能会存在冲突，并且整合了Gson，提供了自定Callback，可以按照泛型，自行解析返回结果，以下是该库的依赖项目：

```java
    compile 'com.android.support:support-annotations:23.1.1'
    compile 'com.squareup.okhttp3:okhttp:3.0.0-RC1'
    compile 'com.google.code.gson:gson:2.5'
```

`library_okhttpserver`是对`library_okhttputils`的扩展，统一了下载管理和上传管理，对项目有需要做统一下载的可以考虑使用该项目，不需要的可以直接使用`library_okhttputils`，不用导入扩展，以下是`library_okhttpserver`的依赖关系：

```java
    compile project(':library_okhttputils')
    compile 'com.j256.ormlite:ormlite-android:4.48'
```

## 3.目前支持
* 一般的get,post,put,delete,head,patch请求
* 基于post,put,patch的文件上传
* 多文件和多参数的表单上传
* 大文件下载和下载进度回调
* 大文件上传和上传进度回调
* 支持session的保持
* 支持链式调用
* 支持自签名网站https的访问，提供方法设置下证书就行
* 支持根据Tag取消请求
* 支持自定义泛型Callback，自动根据泛型返回对象

## 4.扩展功能
### 1.统一的文件下载管理
默认使用的是 get 请求，同时下载数量为3个，支持断点下载，断点信息使用ORMLite数据库框架保存，默认下载路径`/storage/emulated/0/download`，下载路径和下载数量都可以在代码中配置，下载管理使用了服务提高线程优先级，避免后台下载时被系统回收
### 2.统一的文件上传管理
默认使用的是 post 请求，对于需要修改为 put 请求的，只需要修改`library_okhttpserver`中的`UploadTask`第67行代码：
```java
	PostRequest postRequest = OkHttpUtils.post(mUploadInfo.getUrl());
```
修改为
```java
	PostRequest postRequest = OkHttpUtils.put(mUploadInfo.getUrl());
```
该上传管理为简单管理，不支持断点续传和分片上传，只是简单的将所有上传任务使用线程池进行了统一管理，默认同时上传数量为1个

## 5.友情提示
* 该演示Demo中，一般请求Tab演示中，没有做任何UI上的变化，需要看详细的请求过程或是否请求成功的返回数据，自行看打印的log
* 该演示Demo中，一般请求和上传管理的服务器地址是我自己的服务器，公网不可访问，自己测试的时候，自行将请求地址改为自己的服务器接口测试，否者会报请求超时的异常

## 6.用法示例

### 6.1 全局配置
一般在 Aplication，或者基类中，只需要调用一次即可，可以配置调试开关，全局的超时时间，公共的请求头和请求参数等信息
```java
    public void onCreate() {
        super.onCreate();

        System.setProperty("http.proxyHost", "192.168.1.108");
        System.setProperty("http.proxyPort", "8888");

        OkHttpUtils.debug(true, "MyOkHttp");    //是否打开调试
        try {
            OkHttpUtils.getInstance()//
                    .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)//全局的连接超时时间
                    .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)//全局的读取超时时间
                    .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)//全局的写入超时时间
                     //.setCertificates(getAssets().open("srca.cer"), getAssets().open("zhy_server.cer"))//
                    .setCertificates(new Buffer().writeUtf8(CER_12306).inputStream());//设置自签名网站的证书

            RequestHeaders headers = new RequestHeaders();
            headers.put("aaa", "111");
            headers.put("bbb", "222");
            OkHttpUtils.getInstance().addCommonHeader(headers); //全局公共头

            RequestParams params = new RequestParams();
            params.put("ccc", "333");
            params.put("ddd", "444");
            OkHttpUtils.getInstance().addCommonParams(params);  //全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
