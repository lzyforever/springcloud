package com.jack.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ExecutorServiceTest {

    public static void main(String[] args) {
        // test1();
        // test2(10);
        // test3();
        // test4();
        // test5();
        test6();
    }


    /**
     *  创建一个线程线，里面只有一个线程
     */
    public static void test1() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> System.out.println("Asynchronous task"));
        executorService.shutdown();
    }


    /**
     * 创建一个线程池，里面有固定大小的线程
     */
    public static void test2(int num) {
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }

    /**
     * 创建一个线程池，需要获取异步执行的结果
     * 使用Callable和Future组合使用获取返回值
     */
    public static void test3() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 这里使用的Lambda是Callable,如果使用是的Runnable则在任务完之后返回值，不能写在submit之中
        Future<String> future = executorService.submit(() -> {
            System.out.println("Asynchronous callable task");
            return "this is callable result";
        });

        try {
            System.out.println("result: " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    /**
     * 创建一个线程池，需要获取异步执行的结果
     * 使用Runnable和Future组合使用获取返回值
     * 如果任务正常执行结束，则返回null
     */
    public static void test4() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(() -> {
            System.out.println("Asynchronous runnable task");
        });

        try {
            System.out.println("result: " + future.get());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    /**
     * 方法 invokeAny() 接收1个包含 Callable 对象的集合作为参数。调用该方法不会返回 Future 对象，而是返回集合中某1个Callable 对象的结果，
     * 而且无法保证调用之后返回的结果是哪1个 Callable，只知道它是这些 Callable 中1个执行结束的 Callable 对象。
     * 如果1个任务运行完毕或者抛出异常，方法会取消其它的 Callable 的执行。
     */
    public static void test5() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        try {
            String result = executorService.invokeAny(callables);
            System.out.println("result = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }

    /**
     * 方法 invokeAll() 会调用存在于参数集合中的所有 Callable 对象，并且返回1个包含 Future 对象的集合，你可以通过这个返回的集合来管理每1个 Callable 的执行结果。
     * 需要注意的是，任务有可能因为异常而导致运行结束，所以它可能并不是真的成功运行了。但是我们没有办法通过 Future 对象来了解到这个差异。
     */
    public static void test6() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable<String>> callables = new ArrayList<>();
        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        try {
            List<Future<String>> futures = executorService.invokeAll(callables);
            for (Future<String> f : futures) {
                System.out.println("result = " + f.get());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    

}
