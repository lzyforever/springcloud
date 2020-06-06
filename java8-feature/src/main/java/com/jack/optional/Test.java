package com.jack.optional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 测试Optional的使用
 * 实例对象存在则返回，否则提供默认值或者通过方法来设置返回值，即使用orElse/orElseGet方式
 */
public class Test {

    public static void main(String[] args) {
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        // test6();
        // test7();
        test8();
    }

    /**
     * 存在则返回
     * of()的使用
     */
    public static void test1() {
        User jack = new User("jack", 18);
        Optional<User> userOpt = Optional.of(jack);
        User user = userOpt.orElse(null);
        System.out.println(user.getName());
    }

    /**
     * 不存在提供默认值
     * orElse()的使用
     */
    public static void test2() {
        User user = null;
        Optional<User> userOpt = Optional.ofNullable(user);
        User unknow = new User("Unknown", 0 );
        User user2 = userOpt.orElse(unknow);
        System.out.println(user2.getName());
    }

    /**
     * 不可用则可以通过指定函数生成
     * orElseGet()的使用
     */
    public static void test3() {
        User user = null;
        Optional<User> userOpt = Optional.ofNullable(user);
        User user2 = userOpt.orElseGet(() -> new User("DEFAULT", 0));
        System.out.println(user2.getName());
    }

    /**
     * 可用则使用,否则不进行任何操作
     * ifPresent()的使用
     */
    public static void test4() {
        User jack = new User("jack", 18);
        Optional<User> userOpt = Optional.of(jack);
        User luozy = null;
        Optional<User> userOpt2 = Optional.ofNullable(luozy);
        userOpt.ifPresent(u -> System.out.println(u.getName()));
        userOpt2.ifPresent(u -> System.out.println(u.getName()));
    }

    /**
     * 使用map方法获取关联数据
     * map()的使用
     */
    public static void test5() {
        User jack = new User("jack", 18);
        Optional<User> userOpt = Optional.of(jack);
        User luozy = null;
        Optional<User> userOpt2 = Optional.ofNullable(luozy);
        System.out.println(userOpt.map(user -> user.getName()).orElse("DEFAULT"));
        System.out.println(userOpt2.map(user -> user.getName()).orElse("Unknown"));
    }

    /**
     * 使用flatMap方法获取关联数据
     * flatMap()的使用
     */
    public static void test6() {
        List<String> tags = Arrays.asList("a", "g", "b", "f", "c");
        User jack = new User("jack", 18);
        jack.setTags(tags);
        List<String> tags2 = Optional.of(jack).flatMap(user -> Optional.ofNullable(user.getTags())).orElse(Collections.emptyList());
        System.out.println(tags2.isEmpty());
    }

    /**
     * Optional的empty的使用
     */
    public static void test7() {
        Optional<Object> empty = Optional.empty();
        System.out.println(empty.orElse("我是一个空的啊"));

        Optional<Object> nullOpt = Optional.ofNullable(null);
        System.out.println(nullOpt.orElse("这尼玛也是一个空的啊"));

    }

    /**
     * stream、Predicate、Function、Consumer的组合使用
     */
    public static void test8() {
        List<String> tags = Arrays.asList("a", "ga", "ba", "fag", "c");

        Predicate<String> pd = str -> str.indexOf("a") > 0;
        Function<String, String> fc = str -> str + "哈哈";
        Consumer<String> cs = System.out::println;

        tags.stream().filter(pd).map(fc).forEach(cs);
    }


    public static class User {
        private String name = "小强";
        private int age;
        private List<String> tags;

        public User() {
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

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
