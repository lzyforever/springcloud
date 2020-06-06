package com.jack.function;

/**
 * 函数式接口新特性
 *  有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
 */
@FunctionalInterface
public interface Hello {

    /**
     * abstract 方法，只能有一个
     */
    void hello();

    /**
     * 允许定义默认方法
     */
    default void hi() {
        System.out.println("Hello function hi");
    }

    /**
     * 允许定义静态方法
     */
    static void say() {
        System.out.println("Hello static function say");
    }

    /**
     * 允许定义 java.lang.Object里的public方法
     */
    @Override
    boolean equals(Object obj);

}
