##### Android八股

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

##### Android内部通信

eventBus或者广播之类的是比较通用的方式

###### 耗时操作处理

用intentservice，或者线程池，或者handlerthread，或者asynctask。

###### 支持不同像素密度

px=dp*dpi/160。dpi是ppi的近似，最初取160的整数倍做近似处理，方便适配。

###### activity之间通信

intent/bundle；类静态变量；全局变量；

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

##### Java八股

###### 序列化

把内存中的数据变成可以被存储、传输的格式

###### equals与hashcode

hashcode表示指纹信息，hash里面有内存地址

###### get与post的区别

本质上没区别，都是tcp链接。给GET加上request body，给POST带上url参数，技术上是完全行的通的。只是HTTP的规定和浏览器/服务器的限制， 导致他们在应用过程中体现出一些不同。 restful
api的要求是get请求用来从服务器上获得资源，而post是用来向服务器提交数据。另外，对于GET 方式的请求，浏览器会把http
header和data一并发送出去，服务器响应200（返回数据）;而对于POST，浏览器先发送header，服务器响应100 continue，浏览器再发送data，服务器响应200
ok（返回数据）。在网络环境好的情况下，发一次包的时间和发两次包的时间差别基本可以无视。 而在网络环境差的情况下，两次包的TCP在验证数据包完整性上，有非常大的优点。并且，也不是所有浏览器post都发两次，firefox就只发一次。

###### hashmap与hashtable

二者没有本质区别，hash算法大致相同。无非是继承Dictionary类或者实现map接口，对null的容忍度，是否本身限制Synchronized这些区别。

#### hashmap源码解读

哈希表的核心原理是基于和哈希值的桶和链表，缺陷是哈希碰撞。另外linked hashmap就是增加了双向链表的hashmap，用来保持键值对的插入顺序。

##### hashmap的经典实现（jdk1.7）

###### hashmap初始容量是多少？为什么是这个数字？

初始容量是16，负载因子是0.75。容量是2的幂，如果不是2的幂会向上取幂。默认的哈希桶有16个，hash值有42E个，如何把42E个hash值放进16个
hash桶？首先想到的是取模。取模的问题有两点：第一是负数求模还是正数，第二是效率不如位运算。负载因子是一个在时间与空间开销上折衷的选择。 过高的负载因子可以减少空间使用，但是会增加查找消耗。

###### 为什么容量一定是2的幂？

确定hash桶的方式是长度-1然后与hash值进行按位与运算：(length-1)&hash。只有容量是2的n次方的时候，-1操作才能拿到全部都是1的值，这样 进行按位与操作的时候才能快速拿到分布均匀的数组下标。
PS：为了减少哈希碰撞，1.7的hash值做了很多的异或运算，在1.8被废弃。

###### 扩容原理

已使用超过容量*负载因子数量的时候就会产生很多哈希碰撞，开始扩容：对所有元素重新计算hash值，同时容量翻倍。另外即使容量减少也不会出现 缩容操作。

###### jdk1.7的问题

不是线程安全，容易碰到死锁。潜在的安全隐患，致命的哈希碰撞（哈希表变链表，复杂度变成o(n)）（能生成大量相同的hash值）

##### jdk1.8里的hashmap

数组+链表的组合变成了数组+红黑树的组合。注释上说哈希桶容量超过8之后会变成红黑树，在随机产生hashcode的条件下这个概率小于千万分之一，
因此这个很少被用到。hash值的计算变成了和高16位直接进行的异或运算。1.8不会产生死锁，具体分析待查（酷客上找找）

##### array list扩容机制

###### 极简总结

初始容量为10，扩容时每次增加当前容量一半。

###### 详细分析

1 初始化方法：以无参数构造方法创建 ArrayList 时，实际上初始化赋值的是一个空数组。当真正对数组进行添加元素操作时，才真正分配容量。 2 add方法：ArrayList添加元素的实质就相当于为数组赋值 3
判断是否需要扩容：minCapacity（最小扩容量） - elementData.length > 0 4 最小扩容量 添加元素时会比较minCapacity与DEFAULT_CAPACITY，返回较大值。 5 实际扩容操作 int
newCapacity = oldCapacity + (oldCapacity >> 1);然后会比较minCapacity与newCapacity，取较大值作为实际扩容量。
如果minCapacity大于最大容量（MAX_ARRAY_SIZE），则新容量则为Integer.MAX_VALUE。 6 最后简单复制一下数组：elementData = Arrays.copyOf(elementData,
newCapacity);

###### ConcurrentHashMap为什么是线程安全的

采用 CAS 和 synchronized 来保证并发安全，synchronized 只锁定当前链表或红黑二叉树的首节点，这样只要 hash 不冲突，就不会产生并发。 关于锁和并发的知识以后再补，这里先打个记号

###### 补充一点平衡二叉树的笔记

平衡因子被破坏的节点记作根节点<br>
RR旋转： 1.右子树变成新的根节点 2.旧的根节点变成左子树 3.右子树的左子树变成原本根节点的右子树<br>
LL旋转： 1.左子树变成新的根节点 2.旧的根节点变成右子树 3.左子树的右子树变成原本根节点的左子树<br>
LR旋转： 1.根节点的左子树进行右旋转 2.根节点进行左旋转<br>
RL旋转： 1.根节点的右子树进行左旋转 2.根节点进行右旋转<br>
删除操作： 若左孩子非空，右孩子为空，那么把根节点直接赋值为左子树。若右孩子非空，左孩子为空，那么把根节点直接赋值为右子树。左右子树都不为空时，用左子树最大节点或者右子树最小节点代替根节点

###### 多线程操作时为什么调用start而不是run？

调用 start 方法方可启动线程并使线程进入就绪状态，而 run 方法只是 thread 的一个普通方法调用，还是在主线程里执行。

###### sleep和wait最主要的区别是什么？

sleep不会释放锁，wait会释放锁。

###### 数据库的四大特征

原子性，一致性，隔离性，持久性

##### 关于MySQL事务隔离级别

对于两个并发执行的事务，如果涉及到操作同一条记录的时候，可能会发生问题。因为并发操作会带来数据的不一致性，包括脏读、不可重复读、幻读等。 数据库系统提供了隔离级别来让我们有针对性地选择事务的隔离级别，避免数据不一致的问题。

###### Read Uncommitted

隔离级别最低的一种事务级别。在这种隔离级别下，一个事务会读到另一个事务更新后但未提交的数据，如果另一个事务回滚， 那么当前事务读到的数据就是脏数据，这就是脏读（Dirty Read）。

###### Read Committed

在Read Committed隔离级别下，一个事务可能会遇到不可重复读（Non Repeatable Read）的问题。
不可重复读是指，在一个事务内，多次读同一数据，在这个事务还没有结束时，如果另一个事务恰好修改了这个数据，那么，在第一个事务中， 两次读取的数据就可能不一致。

###### Repeatable Read

在Repeatable Read隔离级别下，一个事务可能会遇到幻读（Phantom Read）的问题。幻读是指，在一个事务中，第一次查询某条记录，
发现没有，但是，当试图更新这条不存在的记录时，竟然能成功，并且，再次读取同一条记录，它就神奇地出现了。

###### Serializable

Serializable是最严格的隔离级别。在Serializable隔离级别下，所有事务按照次序依次执行，因此，脏读、不可重复读、幻读都不会出现。
虽然Serializable隔离级别下的事务具有最高的安全性，但是，由于事务是串行执行，所以效率会大大下降，应用程序的性能会急剧降低。 如果没有特别重要的情景，一般都不会使用Serializable隔离级别。

###### cache和buffer的区别

cache：之前已经用过，等会可以复用；buffer：上游存储，下游消费。区别在于buffer只消费一次。

###### ==和equals的区别：

==的作用是判断两个对象的地址是否相等，如果是基本数据类型的话就是比较两个值。equals默认的方法等价于==。String的equals方法被重写过了，
比较的是值。创建String类行的对象的时候，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。 如果没有就在常量池中重新创建一个 String 对象。

    public class test1 {
        public static void main(String[] args) {
            String a = new String("ab"); // a 为一个引用
            String b = new String("ab"); // b为另一个引用,对象的内容一样
            String aa = "ab"; // 放在常量池中
            String bb = "ab"; // 从常量池中查找
            if (aa == bb) // true
                System.out.println("aa==bb");
            if (a == b) // false，非同一对象
                System.out.println("a==b");
            if (a.equals(b)) // true
                System.out.println("aEQb");
            if (42 == 42.0) { // true
                System.out.println("true");
            }
        }
    }

###### 关于常量池的一点代码

Java 基本类型的包装类的大部分都实现了常量池技术。character缓存相关源码：

    private static class CharacterCache {         
        private CharacterCache(){}

        static final Character cache[] = new Character[127 + 1];          
        static {             
            for (int i = 0; i < cache.length; i++)                 
                cache[i] = new Character((char)i);         
            }   
    }

###### hashCode()与 equals()

hashCode()的默认行为是对堆上的对象产生独特值。hashCode() 所使用的杂凑算法也许刚好会让多个对象传回相同的杂凑值，如果 HashSet在对比 的时候，同样的 hashcode 有多个对象，它会使用 equals()
来判断是否真的相同。也就是说 hashcode 只是用来缩小查找成本。另外hashcode 只有在散列表中才有用。

###### try-catch注意事项

如果try和finally里面都有return，那么程序最后会返回finally里面的return语句。面对必须要关闭的资源，我们总是应该优先使用 try-with- resources 而不是try-finally

###### 线程的基本状态

new runnable waiting time-waiting(可以自动返回) terminated blocked(阻塞状态，被锁了)

###### 既然有了字节流,为什么还要有字符流

不管是文件读写还是网络发送接收，信息的最小存储单元都是字节,但是将字节流转换成字符流的过程非常耗时。

###### 线程池

主要就是减少线程开销，有固定线程池（核心线程数和最大线程数相等），带缓冲的线程池（核心线程数为0，每次有新的runnable进来就再创建线程），
单线程线程池（保证线程顺序执行）等等。执行线程的时候execute没有返回值，submit会返回future。

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