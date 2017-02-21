package com.ksea.jvm.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksea on 2017/2/21.
 * 运行时常量池溢出
 * <p>
 * 如果要向运行时常量池中添加内容，最简单的做法就是使用String.intern()这个Native方法
 * <p>
 * 该方法的作用：
 * 如果池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象，
 * 否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
 * <p>
 * 运行时常量池(runtime constant pool)属于分配在方法区(method area)内，那么我们可以通过
 * -XX:PermSize设置其初始大小
 * -XX:MaxPermSize设置其最大值
 * 来限制方法去的大小，从而间接限制其中常量池的容量
 */
public class RuntimeConstantOutOfMemoryErrorDemo {
    public static void main(String[] args) {

        // 使用list集合 让其保持索引的引用，避免Full gc 进行回收
        List<String> list = new ArrayList<String>();
        // PermSize=5M 在Integer的范围内足以让其OOM异常
        int i = 0;

        while (true) {
            list.add(String.valueOf(i++).intern());
        }

    }

    /*
    运行结果:
	Exception in thread "main" java.lang.OutOfMemoryError:PermGen space
	at java.lang.String.intern(Native Method)


    从结果看出运行时常量池溢出，在OutOfMemoryError后面提示：PermGen space
    说明运行时常量池属于方法区(method area)Hotspot虚拟机中的永久带的一部分
    */
}
