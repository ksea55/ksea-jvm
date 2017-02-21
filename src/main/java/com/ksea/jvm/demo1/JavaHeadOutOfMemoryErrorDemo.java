package com.ksea.jvm.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksea on 2017/2/21.
 * java head 内存溢出一场测试
 * 设置JVM参数
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
 * <p>
 * java -verbose[:class|gc|jni] 在输出设备上显示虚拟机运行信息。
 * -Xms20M  分配堆的最小值为20M
 * -Xmx20M 分配堆的最大值为20M
 * -Xmn10M 设置年轻代大小为10M。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。
 * 持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
 */
public class JavaHeadOutOfMemoryErrorDemo {

    static class OOMObject {
    }


    public static void main(String[] args) {
        /**
         * 这里while一直在不停的new OOMObject()创建其对象,最终抛出 java.lang.OutOfMemoryError: Java heap space
         */
        List<OOMObject> oomObjects = new ArrayList<OOMObject>();
        while (true) {
            oomObjects.add(new OOMObject());
        }
        /*运行结果:
        "C:\Program Files\Java\jdk1.7.0_79\bin\java" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:52109,suspend=y,server=n -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.7.0_79\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jce.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jfxrt.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\resources.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\rt.jar;E:\RfdProject\ksea-jvm\target\classes;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.3.4\lib\idea_rt.jar" com.ksea.jvm.demo1.JavaHeadOutOfMemoryErrorDemo
Connected to the target VM, address: '127.0.0.1:52109', transport: 'socket'
[GC [PSYoungGen: 7789K->1016K(9216K)] 7789K->5249K(19456K), 0.0123083 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
[GC-- [PSYoungGen: 9208K->9208K(9216K)] 13441K->19440K(19456K), 0.0303125 secs] [Times: user=0.11 sys=0.00, real=0.03 secs]
[Full GC [PSYoungGen: 9208K->0K(9216K)] [ParOldGen: 10232K->9959K(10240K)] 19440K->9959K(19456K) [PSPermGen: 2753K->2752K(21504K)], 0.1985010 secs] [Times: user=0.38 sys=0.00, real=0.20 secs]
[Full GC [PSYoungGen: 7509K->8003K(9216K)] [ParOldGen: 9959K->7848K(10240K)] 17468K->15852K(19456K) [PSPermGen: 2752K->2752K(21504K)], 0.1905067 secs] [Times: user=0.38 sys=0.00, real=0.19 secs]
[Full GCException in thread "main"  [PSYoungGen: 8003K->8002K(9216K)] [ParOldGen: 7848K->7848K(10240K)] 15852K->15851K(19456K) [PSPermGen: 2752K->2752K(21504K)], 0.1169562 secs] [Times: user=0.28 sys=0.00, real=0.12 secs]
Disconnected from the target VM, address: '127.0.0.1:52109', transport: 'socket'
Heap
 PSYoungGen      total 9216K, used 8192K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 100% used [0x00000000ff600000,0x00000000ffe00000,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 45% used [0x00000000fff00000,0x00000000fff75220,0x0000000100000000)
 ParOldGen       total 10240K, used 7848K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 76% used [0x00000000fec00000,0x00000000ff3aa3d0,0x00000000ff600000)
 PSPermGen       total 21504K, used 2783K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
  object space 21504K, 12% used [0x00000000f9a00000,0x00000000f9cb7f78,0x00000000faf00000)
java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:2245)
	at java.util.Arrays.copyOf(Arrays.java:2219)
	at java.util.ArrayList.grow(ArrayList.java:242)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:216)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:208)
	at java.util.ArrayList.add(ArrayList.java:440)
	at com.ksea.jvm.demo1.JavaHeadOutOfMemoryErrorDemo.main(JavaHeadOutOfMemoryErrorDemo.java:20)

Process finished with exit code 1


         */

    }
}
