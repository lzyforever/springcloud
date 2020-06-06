package com.jack.lambda;

import java.util.function.*;

/**
 * Jdk8 lambda表达式使用
 * java8中一个非常重要的特性就是lambda表达式，可以把它看成一种闭包
 * 它允许把函数当做参数来使用，是面向函数式编程的思想
 * 一定程度是可以使代码看起来更加简洁
 *
 * lambda表达式语法
 * (paramters) -> expression；
 * 或者
 * (paramters) -> {statements;}
 * 展开如：
 *     (Type1 param1, Type2 param2, Type2 param2, ...) -> {
 *         statement1;
 *         statement2;
 *         statement3;
 *         ...
 *         return statementX;
 *     }
 *
 *  可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
 *  可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
 *  可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
 *  可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
 */
public class Test {

    public static void main(String[] args) {
        // testLambda();
        // testNoParam();
        // testSingleParam();
        // testMultipleParam();
        // testClass();
        // testFunction();
        // testInterface();
        testFunReference();
    }


    /**
     * 测试一个匿名内部类来进行测试
     */
    public static void testLambda() {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("内部类的写法");
            }
        }).start();

        new Thread(() -> System.out.println("lambda表达式的写法")).start();
    }

    public interface TestDemo {
        String hi();
    }

    public interface TestDemo2{
        String say(String name);
    }

    public interface TestDemo3{
        String hello(String name, int age);
    }

    /**
     * 测试lambda无参实例化
     */
    public static void testNoParam() {
        // 方式一，简写
        TestDemo noParam = () -> "hello";
        System.out.println(noParam.hi());

        // 方式二
        TestDemo noParam2 = () -> {
            return "hello2";
        };
        System.out.println(noParam2.hi());
    }

    /**
     * 测试单个参数的lambda实例化
     */
    public static void testSingleParam() {
        TestDemo2 single = name -> name;
        System.out.println(single.say("hello single param"));

        TestDemo2 single2 = name -> {
            return name;
        };
        System.out.println(single2.say("hello single param2"));
    }

    /**
     * 测试多个参数的lambda实例化
     */
    public static void testMultipleParam() {
        // 一条语句返回，可省略return的写法
        TestDemo3 multipleParam = (name, age) -> name + " and " + age;
        System.out.println(multipleParam.hello("jack", 18));

        // 多个参数的写法
        TestDemo3 multipleParam2 = (String name, int age) -> name + " 和 " + age;
        System.out.println(multipleParam2.hello("小白", 16));

        // 处理多条语句，就必须加大括号和return
        TestDemo3 multipleParam3 = (name, age) -> {
            System.out.println("name: " + name);
            System.out.println("age: "+ age);
            return name + age;
        };
        System.out.println(multipleParam3.hello("小绿", 20));
    }

    /**
     * 无参构造函数
     */
    public static void testClass() {
        Supplier<User> user = User::new;
        // 等同于
        Supplier<User> user2 = () -> new User();

        System.out.println(user.get().name);
        System.out.println(user2.get().name);
    }

    /**
     * 有参构造函数
     */
    public static void testFunction() {
        // 一个参数的
        Function<String, User> user = name -> new User(name);
        // 等同于
        Function<String, User> user2 = (name) -> new User(name);
        System.out.println(user.apply("hahaha").name);
        System.out.println(user2.apply("小花").name);

        // 二个参数的
        BiFunction<String, Integer, User> user3 = (name, age) -> new User(name, age);
        User user4 = user3.apply("小白", 18);
        System.out.println("name: " + user4.name + " age：" + user4.age);
    }


    public static class User{
        private String name = "小强";
        private int age;

        public User(){
        }

        public User(String name) {
            this.name = name;
        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public interface A {
        String hi();
        String hh();
        default void hello() {
            System.out.println("A.hello");
        }
    }

    public interface B {
        String hi();
        String say();
        default void hello() {
            System.out.println("B.hello");
        }
    }

    public static class C implements A, B {

        @Override
        public String hi() {
            return "C.hi";
        }

        @Override
        public String say() {
            return "C.say";
        }

        @Override
        public String hh() {
            return "C.hh";
        }

        /**
         * 如果继承的两个接口中有两个相同的default默认方法
         * 实现类中不实现hello这个方法，将会编译报错
         */
        @Override
        public void hello() {
            System.out.println("C.hello");
        }

    }

    public static class D{
        public void hello() {
            System.out.println("D.hello");
        }
    }

    public static class E extends D implements A, B {

        @Override
        public String hi() {
            return "E.hi";
        }

        @Override
        public String say() {
            return "E.say";
        }

        @Override
        public String hh() {
            return "E.hh";
        }
    }


    /**
     * 测试接口
     */
    public static void testInterface() {
        A a = new C();
        a.hello();
        System.out.println(a.hh());
        System.out.println(a.hi());

        A e = new E();
        // 这里的E会继承D类中的hello方法
        e.hello();
        System.out.println(e.hh());
        System.out.println(e.hi());
        System.out.println(((E)e).say());

    }

    /**
     * 测试方法引用
     */
    public static void testFunReference() {
        // objectName::instanceMethod，对象实例方法：把表达式的参数值作为instanceMethod方法的参数
        Consumer<String> str = System.out::println;
        // 等同于
        Consumer<String> str2 = (x) -> System.out.println(x);
        str.accept("hello");
        str.accept("bbb");

        str2.accept("abc");

        //ClassName::staticMethod  类的静态方法：把表达式的参数值作为staticMethod方法的参数
        Function<Integer, String> st2 = String::valueOf;
        // 等同于
        Function<Integer, String> st3 = (x) -> String.valueOf(x);
        System.out.println(st2.apply(123));

        //ClassName::instanceMethod  类的实例方法：把表达式的第一个参数当成instanceMethod的调用者，其他参数作为该方法的参数
        BiPredicate<String, String> sp = String::equals;
        // 等同于
        BiPredicate<String, String> sp2 = (x, y) -> x.equals(y);
        System.out.println(sp.test("a", "a"));
        System.out.println(sp.test("a", "A"));
        System.out.println(sp2.test("a", "a"));
        System.out.println(sp2.test("a", "A"));

    }
}
