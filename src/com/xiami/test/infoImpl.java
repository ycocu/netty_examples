package com.xiami.test;

public class infoImpl implements info {
    private String name;

    @Override
    public info addLast(String element) {
        System.out.println("infoImpl=" + element);
        data.add(element);
        System.out.println("data size=" + data.size());
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("set name: " + name);
        this.name = name;
    }
}
