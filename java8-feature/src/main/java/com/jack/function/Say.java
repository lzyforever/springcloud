package com.jack.function;

/**
 * 如果用 Hello hello = Say::new;
 * 调用hello和hi将会调用Hello中的对应方法，不会调用子类的
 * 如果用Hello hello = new Say();   则会按正常逻辑走，调用子类的实现
 */
public class Say implements Hello{

    @Override
    public void hello() {
        System.out.println("Say Function hello");
    }

    @Override
    public void hi() {
        System.out.println("Say Function hi");
    }
}
