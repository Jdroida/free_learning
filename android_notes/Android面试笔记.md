##### Android八股

##### 常见的锁有哪些？lock和sync的区别是什么
sync关键字，读写锁，可重入锁，信号量；lock灵活性更高，需要手动控制锁的释放；

##### 关于surface view与texture view
surface view处理高帧率的人物场景，texture view处理裁剪旋转小窗这些场景

##### AIDL如何实现接口安全
1. 信息内容加密
2. 校验包名和签名
3. 使用signature级别的自定义权限

##### AIDL如何实现双向通信
通过“接口回调”实现，客户端定义的回调接口，服务端使用

##### Webview打开网页慢如何优化
1. 预加载优化
2. 合理配置 WebSettings（包括缓存策略）
3. 注入JavaScript代码，延迟加载非关键资源

###### socket和aidl在应用间交换消息时的区别

操作不一样，socket需要建立连接关闭连接这些操作，aidl不需要；能传输的数据不一样，按理说socket啥都能传，aidl能传的数据类型是固定的， 具体看文档；aidl有Linux的进程ID概念更加安全

###### activity生命周期

onCreate;onStart 可见状态 还没有显示 不能交互;onResume 可见 可交互;onPause;onStop;onDestroy; onRestart 正常启动不会被调用;

###### activity异常情况生命周期补充

onSaveInstanceState;onRestoreInstanceState bundle在这个方法里不为空，在onStart里面可能为空；bundle是散列形式

###### activity状态

active：处于栈顶 可见 可交互;paused：可见 不可交互;stop:不可见 ;killed:系统回收

###### activity启动模式

standard；singleTop（栈顶复用）；singleTask（栈内复用 会销毁目标上面所有activity）； singleInstance（全局唯一性，复用性，独占任务栈）；

###### 支持不同像素密度

px=dp*dpi/160。dpi是ppi的近似，最初取160的整数倍做近似处理，方便适配。

###### okhttp上传一个文件

```
OkHttpClient client = new OkHttpClient();

RequestBody requestBody = new MultipartBody.Builder()
.addFormDataPart("new", "This is my new TODO")
.addFormDataPart("image", "attachment.png",
RequestBody.create(new File("path/of/attachment.png"), MediaType.parse("image/png"))
)
.setType(MultipartBody.FORM)
.build();

Request postRequest = new Request.Builder()
.url("https://mytodoserver.com/new")
.post(requestBody)
.build();

try {
Response response = client.newCall(postRequest).execute();
System.out.println(response.body().string());
} catch (IOException e) {
e.printStackTrace();
}

```

###### activity与fragment通信

bundle getArgument可以获取bundle；直接在activity定义方法；

###### fragment与activity通信

接口回调 1.在fragment中定义内部回调方法 2.attach方法中检查 3.detach方法释放activity

###### activity与service通信

1 绑定服务 利用service_connection接口（自己实现binder内部类）；2简单通信 intent传值；3定义callback接口(自己实现binder内部类)

###### service与thread

thread就是正常的线程生命周期，缺陷在于**无法控制**。service运行在主线程。
###### service生命周期
startService：onCreate onStartCommand（执行多次） onDestroy；
bindService：onCreate onBind onUnbind onDestroy；如果已经绑定service，在解绑之前无法销毁。
###### service与intentService
intentService自动停止，内部有子线程处理耗时操作。源码层面上，intentService继承了service，在onCreate内创建handlerThread，并且
绑定looper。
###### 启动服务和绑定服务
启动优先级比绑定高，无论哪种状态都可以直接开启服务。启动服务后即使相关activity被销毁，service也不会销毁。
###### 序列化

把内存中的对象写入磁盘。parcelable接口与serializable接口。

###### AIDL

创建AIDL；服务端新建service，创建binder对象；客户端：实现service_connection,bindService

###### handler

handler获取当前线程looper，looper取出messageQueue，完成三者绑定。messageQueue内部是链表实现。threadLocal获取当前线程looper。
创建looper的时候（构造函数）创建了messageQueue。handler获取looper是用的myLooper方法，点进去就是threadLocal的get方法。在looper
的loop方法里面有一个死循环，消息不为空的时候回调dispatchMessage方法，最后handler调用handleMessage方法。looper是在主线程的，因此 在子线程中不能直接new handler。

###### asyncTask

本质是handler和线程池的封装，实例只能在主线程中创建。最大线程数=cpu核心数*2+1。

###### aidl

服务端创建aidl；<br>
服务端实现aidl并向客户端开放接口（创建service ，声明ibinder，实现stub实例。stub实例中实现的方法就是后面供客户端调用的方法）；
<br>客户端调用aidl（包目录保持一致，然后绑定服务即可，绑定的时候走stub的asinterface方法）；

###### butterKnife原理

1 扫描所有Java代码中的butterKnife注解 2 ButterKnifeProcessor会根据className生成viewBinder 3 调用bind方法加载生成的viewBinder类、

##### 网络基础八股

##### 浏览器输入地址到返回结果发生了什么

1、DNS解析，此外还有DNSy优化（DNS缓存、DNS负载均衡） ；2、TCP连接 ；3、发送HTTP请求 ；4、服务器处理请求并返回HTTP报文 ； 5、浏览器解析渲染页面 ；6、连接结束

###### socket连接与http连接

一般socket建立起来的是tcp连接，HTTP连接使用的是“请求—响应”的方式，socket连接适用于服务器主动向客户端发送数据的场景。http连接可以是 长连接也可以是短连接，看实际业务需求，短连接就是一次交互之后就会断开。

###### https四次握手

1 客户端请求建立SSL链接，并向服务端发送一个随机数–Client random和客户端支持的加密方法，比如RSA公钥加密，此时是明文传输。<br>
2 服务端回复一种客户端支持的加密方法、一个随机数–Server random、授信的服务器证书和非对称加密的公钥。<br>
3 客户端收到服务端的回复后利用服务端的公钥，加上新的随机数–Premaster secret 通过服务端下发的公钥及加密方法进行加密，发送给服务器。<br>
4 服务端收到客户端的回复，利用已知的加解密方式进行解密，同时利用Client random、Server random和Premaster secret通过一定的算法 生成HTTP链接数据传输的对称加密key – session
key。<br>
<br>个人总结和tcp三次握手基本一样，发送的随机数作用就和syn和ack的作用差不多，区别就是每次携带了支持的加密类型或者公钥。最后多出来的一次 握手其实就是服务端做的解密工作。

###### 对称加密与非对称加密

AES与DES都是对称加密，DES因为密钥太短已被弃用。非对称加密的加密算法和解密算法是相同的，只是公钥和私钥不同。数字签名就是签名方使用私钥加密，
各解密方用公钥解密。签名算法也可以用更简便的方式（对哈希值签名）。主流算法是RSA与DSA。

###### 常见编码

base64：将二进制数据转换成由64个字符组成的字符串的编码算法；urlencoding：将URL中的保留字符使用百分号"%"进行编码，为了消除歧义。

##### 操作系统八股

###### 软连接与硬链接

软连接就是快捷方式，硬链接就是路径别名。当所有硬链接全删掉时，文件才真正被删除