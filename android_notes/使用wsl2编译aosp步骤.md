1. 首先安装并启动wsl2，这里我选的是Ubuntu20.04
2. 替换清华源，科学上网：
   `sudo vim /etc/apt/sources.list`
   将内容替换为：

```
#添加清华源
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-updates main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-backports main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-backports main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-security main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-security main restricted universe multiverse multiverse
```

然后执行
`sudo apt update`

3. 开始准备aosp代码

```
mkdir aosp
cd aosp
#准备repo
curl https://mirrors.tuna.tsinghua.edu.cn/git/git-repo -o repo
chmod +x repo
export REPO_URL='https://mirrors.tuna.tsinghua.edu.cn/git/git-repo'
#建立仓库 这里我选了Android13
./repo init -u https://mirrors.tuna.tsinghua.edu.cn/git/AOSP/platform/manifest -b android-13.0.0_r1	
#同步代码
./repo sync
```

4. 编译aosp

```
# 配置aosp脚本环境
source build/envsetup.sh
# 选择镜像（这里我试过65和67，不同的镜像可能会遇到不同的问题）
lunch 
# 开始编译
m
# 可选项 google推荐安装的一些包
sudo apt install git-core gnupg flex bison build-essential zip curl zlib1g-dev libc6-dev-i386 x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev libxml2-utils xsltproc unzip fontconfig
# 缺少so库的情况 比如libncurses5，注意i386可能找不到，但这个经我测试也是非必须的
sudo add-apt-repository universe
sudo apt-get install libncurses5 libncurses5:i386
# 启动模拟器
emulator
```
