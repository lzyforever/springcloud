package com.jack.stream;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 测试Stream的使用
 */
public class Test {

    /**
     *  Stream of方法的使用
     *  of(T... values)：返回含有多个T元素的Stream
     *  of(T t)：返回含有一个T元素的Stream
     */
    private static void testOf() {
        // 返回含有一个T元素的Stream
        Stream<String> single = Stream.of("a");
        // 返回含有多个T元素的Stream
        Stream<String> multiple = Stream.of("a", "b", "c");
    }

    /**
     * Stream generator方法的使用
     * 返回一个无限长度的Stream,其元素由Supplier接口的提供。
     */
    private static void testGenerate() {
        Stream<String> generate = Stream.generate(() -> "a");
        Stream<Double> generateA = Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });

        //  lambda写法
        Stream<Double> generateB = Stream.generate(() -> Math.random());
        // 等同于
        Stream<Double> generateC = Stream.generate(Math::random);
    }

    /**
     * Stream iterate方法使用
     * 返回的也是一个无限长度的Stream与generate方法不同的是，其是通过函数f迭代对给
     * 指定的元素种子而产生无限连续有序Stream，其中包含的元素可以认为是：seed，f(seed),f(f(seed))无限循环
     */
    private static void testIterate() {
        Stream<Integer> iterate = Stream.iterate(1, item -> item + 2);
        // 无限流处理，这里通过limit指定其迭代的范围数量
        iterate.limit(10).forEach(System.out::println);
    }

    /**
     * Stream empty 方法使用
     * empty方法返回一个空的顺序Stream，该Stream里面不包含元素项。
     */
    private static void testEmpty() {
        Stream<Object> empty = Stream.empty();
    }

    /**
     * Stream 对Collection接口和数组的默认方法
     */
    private static void testCollection() {
        // 对数组的操作支持
        String chars[] = new String[]{"a", "b", "c"};
        Arrays.stream(chars).forEach(System.out::println);

        // 对集合的操作支持
        List list = Arrays.asList(chars);
        list.stream().forEach(System.out::println);
    }

    /**
     * Stream concat方法的使用
     * concat 将两个Stream连接在一起，合成一个Stream。若两个输入的Stream都时排序的，则新Stream也是排序的；
     * 若输入的Stream中任何一个是并行的，则新的Stream也是并行的；
     * 若关闭新的Stream时，原两个输入的Stream都将执行关闭处理。
     */
    private static void testConcat() {
        Stream.concat(Stream.of(1, 2, 3), Stream.of(4, 5))
                .forEach(integer -> System.out.print(integer + "  "));
    }

    /**
     * Stream distinct方法的使用
     * 去除掉原Stream中重复的元素，生成的新Stream中没有没有重复的元素
     */
    private static void testDistinct() {
        Stream.of(1, 1, 3, 4, 3).distinct().forEach(System.out::println);
    }

    /**
     * Stream filter方法的使用
     * 对原Stream按照指定条件过滤，过滤出满足条件的元素。
     */
    private static void testFilter() {
        Stream.of(1, 1, 3, 4, 3).filter(x -> x > 2).forEach(System.out::println);
    }

    /**
     * Stream map方法的使用
     * 将对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。
     * 为了提高处理效率，官方已封装好了，三种变形：mapToDouble，mapToInt，mapToLong
     * 将原Stream中的数据类型，转换为double,int或者long类型。
     */
    private static void testMap() {
        Stream.of("a", "b", "c")
                .map(item -> item.toUpperCase())
                // .map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println(Stream.of(1, 2, 4).mapToInt(item -> item).sum());
        System.out.println(Stream.of("abc", "123", "ee").mapToInt(String::length).sum());
    }

    /**
     * Stream flatMap方法的使用
     * 与map方法类似，都是将原Stream中的每一个元素通过转换函数转换
     * 不同的是，该换转函数的对象是一个Stream，也不会再创建一个新的Stream，而是将原Stream的元素取代为转换的Stream。
     */
    private static void testFlatMap() {
        List<User> users = getUsers(6);
        users.stream()
                .flatMap(s -> s.getTags().stream())
                .forEach(System.out::println);

        Stream.of("a", "b", "c").flatMap(s -> Stream.of(s.toUpperCase())).forEach(System.out::println);
    }

    /**
     * Stream peek方法的使用
     * 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例)
     */
    private static void testPeek() {
        Stream.of("a", "b", "c")
                //优先执行
                .peek(s -> System.out.println("peek:" + s))
                .forEach(System.out::println);
    }

    /**
     * Stream skip方法的使用
     * 将过滤掉原Stream中的前N个元素，返回剩下的元素所组成的新Stream。
     * 如果原Stream的元素个数大于N，将返回原Stream的后的元素所组成的新Stream；
     * 如果原Stream的元素个数小于或等于N，将返回一个空Stream。
     */
    private static void testSkip() {
        Stream.of("a", "b", "c").skip(2)
                .forEach(System.out::println);
    }

    /**
     * Stream sorted方法的使用
     * 将对原Stream进行排序，返回一个有序列的新Stream。sorterd有两种变体sorted()，sorted(Comparator)
     * 前者将默认使用Object.equals(Object)进行排序，而后者接受一个自定义排序规则函数(Comparator)，可自定义进行排序。
     */
    private static void testSorted() {
        Stream.of(5, 6, 3, 9, 1)
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------------------------------");

        Stream.of(5, 6, 3, 9, 1)
                .sorted(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        //asc
                        return o1 - o2;
                    }
                })
                .forEach(System.out::println);
        System.out.println("--------------------------------");
        // 等同于
        Stream.of(5, 6, 3, 9, 1)
                //asc
                .sorted(Comparator.comparingInt(o -> o))
                .forEach(System.out::println);

        System.out.println("--------------------------------");

        Stream.of(5, 6, 3, 9, 1)
                //desc
                .sorted(((o1, o2) -> o2 - o1))
                .forEach(System.out::println);
        System.out.println("+++++++++++++++++++++++++++++++");
    }

    /**
     * Stream count方法的使用
     * 将返回Stream中元素的个数
     */
    private static void testCount() {
        long count = Stream.of(1, 2, 3, 4, 5).count();
        System.out.println("count:" + count);
    }

    /**
     * Stream forEach方法的使用
     * 用于遍历Stream中的所元素，避免了使用for循环，让代码更简洁，逻辑更清晰。
     */
    private static void testForEach() {
        Stream.of("a", "b", "c").forEach(System.out::println);
    }

    /**
     * Stream forEachOrdered方法的使用
     * forEachOrdered 与forEach类似，都是遍历Stream中的所有元素
     * 不同的是，如果该Stream预先设定了顺序，会按照预先设定的顺序执行（Stream是无序的），默认为元素插入的顺序。
     */
    private static void testForEachOrdered() {
        Stream.of(5, 2, 1, 4, 3)
                .forEachOrdered(integer ->
                        System.out.println("integer:" + integer)
                );
    }

    /**
     * Stream max方法的使用
     * max 根据指定的Comparator，返回一个Optional，该Optional中的value值就是Stream中最大的元素。
     */
    private static void testMax() {
        // 取的是最小，o2-o1说明是asc
        Optional<Integer> max = Stream.of(5, 2, 2, 3, 4, 8)
                .max((o1, o2) -> o2 - o1);

        // 取最大，comparingInt方法返回的是最大 desc
        Optional<Integer> max2 = Stream.of(5, 2, 2, 3, 4, 8)
                .max(Comparator.comparingInt(x -> x));

        // 取的是最小，o1-o2说明是desc，同上面max2一样，是上面max2的简写
        Optional<Integer> max3 = Stream.of(1, 2, 3, 4, 5)
                .max((o1, o2) -> o1 - o2);

        int max4 = Stream.of(1, 2, 3, 4, 5)
                .mapToInt(x -> x).max().getAsInt();

        System.out.println("min = " + max.get()+ "  max2 = " + max2.orElse(-1) + "  max3 = " + max3.orElse(-1) + "  max4 = " + max4);

        // 按年龄从小到大排，名字排升序来，这是一个组合排序
        getUsers(6).stream()
                .sorted(Comparator.comparing(User::getAge).thenComparing(User::getName))
                .forEach(u -> System.out.println(u.getName()));
    }

    /**
     * Stream mix方法使用
     * min 根据指定的Comparator，返回一个Optional，该Optional中的value值就是Stream中最小的元素。
     */
    private static void testMix() {
        Optional<Integer> min = Stream.of(1, 2, 3, 4, 5)
                .min((o1, o2) -> o1 - o2);
        System.out.println("min:" + min.get());
    }

    /**
     * Stream reduce方法的使用
     * 1、reduce((sum, item) -> { ... }); //返回Optional，因为可能存在为空的情况
     * 2、reduce(0, (sum, item) -> { ... }); /返回对应类型，不存在为空的情况
     */
    private static void testReduce() {
        //无初始值，第一个参数为stream的第一个元素，第二个参数为stream的第二个元素，计算的结果赋值给下一轮计算的sum
        Optional<Integer> optional = Stream.of(1, 2, 3, 4, 5).reduce((sum, item) -> {
            System.out.println("sum before:" + sum);
            System.out.println("item:" + item);
            sum = sum + item;
            System.out.println("sum after:" + sum);

            return sum;
            //return Integer.sum(sum, item);
        });
        //等同于
        Optional<Integer> optional1 = Stream.of(1, 2, 3, 4, 5).reduce((sum, item) ->
                Integer.sum(sum, item)
        );
        //等同于
        Optional<Integer> optional2 = Stream.of(1, 2, 3, 4, 5).reduce(Integer::sum);
        System.out.println("integer = " + optional.orElse(-1));

        System.out.println("-------------------------------");

        //给定初始值，第一个参数为初始值，第二个参数为stream的第一个元素，计算的结果赋值给下一轮计算的sum
        Integer reduce = Stream.of(1, 2, 3, 4, 5).reduce(5, (sum, item) -> {
            System.out.println("sum2 before:" + sum);
            System.out.println("item:" + item);
            sum = sum + item;
            System.out.println("sum2 after:" + sum);
            return sum;
        });
        //等效
        Integer reduce2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (sum, item) ->
                Integer.sum(sum, item)
        );
        //等效
        Integer reduce3 = Stream.of(1, 2, 3, 4, 5).reduce(0, Integer::sum);
        System.out.println("reduce = " + reduce);
    }

    /**
     * Stream collect方法的使用
     */
    private static void testCollect() {
        List<Integer> toList = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toList());
        // 等同于
        List<Integer> toList2 = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("toList: " + toList);
        System.out.println("toList2: " + toList2);

        System.out.println("-------------------------------");

        Set<Integer> toSet = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toSet());
        // 等同于
        Set<Integer> toSet2 = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toCollection(() -> new TreeSet()));
        // 等同于
        Set<Integer> toSet3 = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("toSet: " + toSet);
        System.out.println("toSet2: " + toSet2);
        System.out.println("toSet3: " + toSet3);

        System.out.println("-------------------------------");

        //(value1, value2) -> value1  用前面的value覆盖后面的value，保持不变
        List<User> userList = getUsers(5);
        userList.add(new User("jack", 18));
        Map<Integer, String> toMap = userList.stream()
                .collect(Collectors.toMap(User::getAge, User::getName, (value1, value2) -> value1));
        System.out.println("(value1, value2) -> value1");
        toMap.forEach((k, v) -> System.out.println(k + "-" + v));

        System.out.println("-------------------------------");

        // 对value值进行了限定不能为null，否则抛出空指针异常
        // userList.add(new User(null, 12));
        // (value1, value2) -> value2  用后面的value覆盖前面的value
        Map<Integer, String> toMap2 = userList.stream()
                .collect(Collectors.toMap(User::getAge, User::getName, (value1, value2) -> value1));
        System.out.println("(value1, value2) -> value2");
        toMap2.forEach((k, v) -> System.out.println(k + "-" + v));

        System.out.println("-------------------------------");

        // 解决value值为null方式
        userList.add(new User(null, 8));
        Map<Integer, String> toMap3 = userList.stream()
                .collect(HashMap::new, (m, u) -> m.put(u.getAge(), u.getName()), HashMap::putAll);
        toMap3.forEach((k, v) -> System.out.println(k + "-" + v));

        System.out.println("-------------------------------");

        Optional<Integer> maxBy = Stream.of(1, 2, 3, 4)
                .collect(Collectors.maxBy(Comparator.comparingInt(o -> o)));
        System.out.println("maxBy:" + maxBy.get());

        System.out.println("-------------------------------");

        Long counting = Stream.of(1, 2, 3, 4)
                .collect(Collectors.counting());
        // 等同于
        Long counting2 = Stream.of(1, 2, 3, 4).count();
        System.out.println("counting:" + counting);
        System.out.println("counting2:" + counting2);

        System.out.println("-------------------------------");

        //分割数据块
        Map<Boolean, List<Integer>> partitioningBy = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.partitioningBy(item -> item > 3));
        //partitioningBy : {false=[1, 2, 3], true=[4, 5]}
        System.out.println("partitioningBy : " + partitioningBy);

        Map<Boolean, Long> collect = Stream.of(1, 2, 3, 4)
                .collect(Collectors.partitioningBy(item -> item > 3, Collectors.counting()));
        System.out.println("collect: " + collect);

        System.out.println("-------------------------------");

        //数据分组
        Map<Boolean, List<Integer>> groupingBy = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.groupingBy(item -> item > 3));
        //partitioningBy : {false=[1, 2, 3], true=[4, 5]}
        System.out.println("groupingBy : " + groupingBy);

        System.out.println("-------------------------------");

        //字符串
        String joining = Stream.of("a", "b", "c", "d")
                .collect(Collectors.joining(","));
        System.out.println(joining);

        String joining2 = Stream.of("a", "b", "c", "d")
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(joining2);

        System.out.println("-------------------------------");

        System.out.println(userList);
        User uu = new User("haha", 12);
        uu.setTags(Arrays.asList("tag1", "tag2", "tag3"));
        userList.add(uu);

        // 根据age分组
        Map<Integer, List<User>> collect1 = userList.stream().collect(Collectors.groupingBy(User::getAge));
        // 将age映射为age集合
        List<Integer> collect2 = userList.stream().collect(Collectors.mapping(User::getAge, Collectors.toList()));
        // 根据age分组后将tags映射成集合
        Map<Integer, List<List<String>>> collect3 = userList.stream()
                .collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getTags, Collectors.toList())));
        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);
    }


    private static List<User> getUsers(int size) {
        List<User> list = new ArrayList<>(size);
        User user = null;
        while (size > 0) {
            user = new User("name" + size, size);
            user.setTags(Arrays.asList("tag" + size, "tag" + (size + 1), "tag" + (size * size)));
            list.add(user);
            size --;
        }
        return list;
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


    public static void main(String[] args) {

        // 流的来源
        // testOf();
        // testGenerate();
        // testIterate();
        // testEmpty();
        // testCollection();

        // 流的操作
        // testConcat();
        // testDistinct();
        // testFilter();
        // testMap();
        // testFlatMap();
        // testPeek();
        // testSkip();
        // testSorted();
        // testCount();
        // testForEach();
        // testForEachOrdered();
        // testMax();
        // testMix();

        // testReduce();

        testCollect();


    }
}
