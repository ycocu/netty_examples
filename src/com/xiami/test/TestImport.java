package com.xiami.test;
import com.xiami.test.info;
public class TestImport {
    public static void main(String[] args) {
        info A = new infoImpl();
        infoImpl B = new infoImpl();
        A.addLast("a");
        A.addLast("b");
        B.setName("Jack");
    }

}
