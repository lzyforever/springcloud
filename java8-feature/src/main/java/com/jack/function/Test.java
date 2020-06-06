package com.jack.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 测试函数式接口新特性
 */
public class Test {
    public static void main(String[] args) {
        // testFunctional();
        // testFunction();
        // testConsumer();
        // testSupplier();
        testPredicate();
    }

    public static void testFunctional() {
        Hello hello = Say::new;
        hello.hello(); // 无输出，因为调用了父类的方法
        hello.hi(); // Hello function hi
        Hello.say(); // Hello static function say
        System.out.println(hello.equals("a")); // false

        Hello hello2 = new Say();
        hello2.hello(); // Say Function hello
        hello2.hi(); // Say Function hi
        Hello.say(); // Hello static function say
        System.out.println(hello2.equals("a")); // false
    }

    public static class User {
        private String name;
        private int age;

        public User() {
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

    /**
     * Function<T, R> lambda写法
     */
    private static Function<User, String> userFormat = User::getName;
    // 或者
    private static Function<User, String> userFormat2 = u -> u.getName();

    /**
     * 测试Function对象
     * Function<T, R> 接受一个入参T，返回R类型对象，使用apply方法获取方法执行的内容
     * R apply(T t);
     */
    public static void testFunction() {
        User user = new User("小白", 12);
        String name = userFormat.apply(user);
        System.out.println(name);
    }

    /**
 　　* Consumer<T> lambda写法
 　　*/
    private static Consumer<User> userPrint = user -> System.out.println(user.getName());


    /**
     *  测试 Consumer对象
     *  Consumer<T> 接受一个参数T，没有返回值，使用accept方法对参数执行操作
     *  void accept(T t);
     */
    public static void testConsumer() {
        User user = new User("小白", 12);
        userPrint.accept(user);
    }

    /**
     * Supplier<T> lambda写法
     */
    private static Supplier<User> userSupplier = () -> new User("小花", 18);

    /**
     * 测试Supplier对象
     * Supplier<T> 没有入参，返回T类型结果，使用get方法获取返回结果
     * T get();
     */
    private static void testSupplier() {
        User user = userSupplier.get();
        String name = user.getName();
        System.out.println(name);
    }

    /**
     * Predicate<T> lambda写法
     */
    private static Predicate<User> userPredicate = user -> !user.getName().isEmpty();

    /**
     * 测试Predicate对象
     * Predicate<T> 接受一个入参，返回结果为true或者false,使用test方法进行测试并返回测试结果
     * boolean test(T t);
     */
    private static void testPredicate() {
        User user = new User("张三", 18);
        boolean flag = userPredicate.test(user);
        System.out.println(flag);
    }



}
