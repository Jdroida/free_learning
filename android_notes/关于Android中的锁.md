# 锁与并发相关知识点总结

## 目录
- 锁的分类与概念
- `synchronized` 关键字
- `volatile` 关键字
- `Lock` 接口（及其常用实现）
- Atomic 原子类
- Kotlin 协程中的锁（`Mutex` 等）
- 面试重点汇总

---

## **一、锁的分类与概念**

### 1. 锁的基本分类
锁可以按照以下维度分类：
1. **互斥锁 / 共享锁**:
    - **互斥锁**: 同一时间只有一个线程可以获取（如 `ReentrantLock`）
    - **共享锁**: 多个线程可以同时读取数据（如 `ReadWriteLock` 的读锁）
2. **公平锁 / 非公平锁**:
    - **公平锁**: 按获取锁请求的先后顺序分配锁
    - **非公平锁**: 不保证获取顺序，性能略优
3. **乐观锁 / 悲观锁**:
    - **悲观锁**: 假定会冲突，操作前先加锁阻止其他线程（如 `synchronized`）
    - **乐观锁**: 假定不会冲突，直接尝试操作，失败后重试（如 CAS）
4. **可重入锁 / 不可重入锁**:
    - **可重入锁**: 同一线程可以多次获取同一把锁，不会自己和自己阻塞（如 `ReentrantLock`）
    - **不可重入锁**: 获取后再次尝试会阻塞
5. **自旋锁 / 非自旋锁**:
    - **自旋锁**: 循环尝试获取锁，不挂起线程（适合短时间锁）
    - **非自旋锁**: 获取失败时挂起线程

---

## **二、`synchronized` 关键字**

### 1. 能修饰哪些
| 形式                 | 锁的对象        | 粒度                              |
|----------------------|----------------|----------------------------------|
| 修饰实例方法         | `this` 实例锁   | 同一实例的该方法互斥               |
| 修饰静态方法         | `Class` 类锁    | 全局互斥（所有实例）               |
| 修饰代码块 (this)    | `this` 实例锁   | 锁范围可控，同一实例的互斥          |
| 修饰代码块 (任意对象) | 指定的对象       | 灵活控制，锁不同对象互不影响          |

### 2. 特性总结
| 特性            | 说明                                   |
|----------------|--------------------------------------|
| 可见性          | 进入同步块前强制从主内存读取最新值，退出同步块后写回主内存 |
| 原子性          | 同步块内代码由同一线程执行，其他线程阻塞 |
| 类锁 vs 实例锁   | 类锁和实例锁分别独立互斥            |
| 自动释放        | `synchronized` 会自动释放锁，避免死锁 |

### 3. 用法示例
```kotlin
class Example {
    @Synchronized  // 锁的是当前实例 (this)
    fun instanceMethod() { ... }

    companion object {
        @Synchronized  // 锁的是 Class 对象
        fun staticMethod() { ... }
    }

    fun syncBlock() {
        synchronized(this) { ... }  // 锁 this 实例
        synchronized(anotherObject) { ... }  // 锁任意对象
    }
}
```

---

## **三、`volatile` 关键字**

`volatile` 是轻量级同步机制，适合 **一写多读** 且写操作是简单赋值的场景。

### 1. 作用
1. **可见性**: 写后立刻刷新主内存，读时从主内存加载
2. **有序性**: 禁止指令重排序（写-读之间插入内存屏障）
3. **不保证原子性**: 如 `count++` 仍然需要加锁或使用 `AtomicInteger`

### 2. 适合场景
- 状态标志位（如退出信号）
- 双重检查锁定（DCL 单例）

### 3. 不能保证的
- **修改引用内容**: 对象本身的字段需要额外同步（`volatile` 只保证引用的可见性）

### 示例
```kotlin
@Volatile
var isRunning = true

fun stop() {
    isRunning = false  // 确保其他线程能立即看到最新值
}
```

---

## **四、`Lock` 接口**

### 1. 常用实现类
| 实现               | 特点与场景                                              |
|------------------|-----------------------------------------------------|
| `ReentrantLock`    | 可重入锁，支持公平锁、非公平锁、`tryLock`、条件等待 |
| `ReentrantReadWriteLock` | 读写分离锁，适合读多写少场景                              |
| `StampedLock`      | 乐观读策略，读性能更高（Java 8+ 支持）                   |

### 2. 关键方法
| 方法                  | 说明                                     |
|---------------------|----------------------------------------|
| `lock()`            | 获取锁，阻塞等待                         |
| `tryLock()`         | 尝试获取锁，不阻塞，失败立即返回             |
| `tryLock(timeout)`  | 尝试获取锁，带超时                        |
| `lockInterruptibly` | 可响应中断的锁方法                       |
| `unlock()`          | 释放锁，需要手动调用                      |
| `newCondition()`    | 创建条件变量，支持精准控制线程通信             |

### 示例
```kotlin
val lock = ReentrantLock()

fun safeMethod() {
    lock.lock()
    try {
        // 临界区代码
    } finally {
        lock.unlock()  // 必须释放锁！
    }
}
```

---

## **五、Atomic 原子类**

- 无锁并发策略，基于 CAS 实现，适合简单变量的高性能操作
- 常用类：
    - `AtomicInteger`, `AtomicLong`
    - `AtomicReference<T>`
    - `AtomicIntegerArray`

```kotlin
val count = AtomicInteger(0)

fun increment() {
    count.incrementAndGet()  // 原子递增
}
```

---

## **六、Kotlin 协程中的锁**

### 1. `Mutex`
- 非阻塞锁，协程挂起而非线程阻塞

```kotlin
val mutex = Mutex()
suspend fun safeAction() {
    mutex.withLock {
        // 临界区代码
    }
}
```

### 2. 单线程 Dispatcher
- 单线程调度所有操作，不需要锁

```kotlin
val dispatcher = newSingleThreadContext("SingleThread")
suspend fun update() = withContext(dispatcher) {
    // 所有操作串行化
}
```

---

## **七、面试重点汇总**

### 1. `synchronized` 和 `Lock` 的对比
| 比较点         | `synchronized`                        | `Lock`                             |
|---------------|---------------------------------------|------------------------------------|
| 获取失败       | 阻塞等待                              | 可用 `tryLock` 立即返回             |
| 公平性         | 不支持                                | 可选公平/非公平锁                    |
| 等待中断       | 不支持                                | 支持 `lockInterruptibly`            |
| 条件变量       | 只有一个                              | 可用 `newCondition` 创建多个条件变量  |
| 锁范围         | 仅支持方法或代码块                     | 可跨代码块                          |
| 自动释放       | 自动释放                              | 必须手动释放，忘记会死锁              |

### 2. `volatile` 和 `synchronized` 的对比
| 比较点         | `volatile`                           | `synchronized`                   |
|---------------|---------------------------------------|----------------------------------|
| 可见性         | ✅ 保证                               | ✅ 保证                           |
| 原子性         | ❌ 不保证                            | ✅ 保证                          |
| 有序性         | ✅ 禁止指令重排                        | ✅ 禁止指令重排                   |
| 开销           | 较低（无锁）                         | 开销较高（需要加解锁）             |

### 3. 实战场景速查
| 情景                  | 推荐                        |
|-----------------------|---------------------------|
| 状态标志、引用切换      | `@Volatile` 或 `AtomicReference` |
| 计数器               | `AtomicInteger`            |
| 协程中共享数据         | `Mutex.withLock`           |
| 协程中串行化操作       | 单线程 Dispatcher           |
| 普通线程争夺锁         | `ReentrantLock`           |
| 高并发读多写少         | `ReentrantReadWriteLock`   |