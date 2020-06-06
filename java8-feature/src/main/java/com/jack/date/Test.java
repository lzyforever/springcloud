package com.jack.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间新特性
 *
 * Java 8通过发布新的Date-Time API (JSR 310)来进一步加强对日期与时间的处理。
 * java.util.Date和SimpleDateFormatter都不是线程安全的，而LocalDate和LocalTime和最基本的String一样，是
 * 不变类型，不但线程安全，而且不能修改。
 *
 * 以下为一些常用时间对象：
 *
 * Instant：表示时刻，不直接对应年月日信息，需要通过时区转换
 * LocalDateTime: 表示与时区无关的日期和时间信息，不直接对应时刻，需要通过时区转换
 * LocalDate：表示与时区无关的日期，与LocalDateTime相比，只有日期信息，没有时间信息
 * LocalTime：表示与时区无关的时间，与LocalDateTime相比，只有时间信息，没有日期信息
 * ZonedDateTime： 表示特定时区的日期和时间
 * ZoneId/ZoneOffset：表示时区
 *
 * 最新JDBC映射将把数据库的日期类型和Java 8的新类型关联起来：
 *
 * SQL -> Java
 *  --------------------------
 *  date -> LocalDate
 *  time -> LocalTime
 *  timestamp -> LocalDateTime
 * 再也不会出现映射到java.util.Date其中日期或时间某些部分为0的情况了
 * 
 */
public class Test {

    /**
     * 日期格式
     */
    private final static String PATTERN_1 = "yyyy-MM-dd";

    /**
     * 小时为0-12
     */
    private final static String PATTERN_2 = "yyyy-MM-dd hh:mm:ss";
    /**
     * 小时为0-24
     */
    private final static String PATTERN_3 = "yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) {
        // clock();
        // localDate();
        // localTime();
        // localDateTime();
        // zoneDateTime();
        // duration();
        // date2Str();
        // str2Date();
        // System.out.println(date2LocalDateTime());
        // System.out.println(localDateTime2Date());
        // System.out.println(localDateTime2LocalDate());
        // System.out.println(localDateTime2LocalTime());
        // System.out.println(localDate2Date());
    }

    /**
     * Clock类，它通过指定一个时区，然后就可以获取到当前的时刻，日期与时间。
     * Clock可以替换System.currentTimeMillis()与TimeZone.getDefault()。
     */
    public static void clock() {
        // Get the system clock as UTC offset
        final Clock clock = Clock.systemUTC();
        System.out.println(clock.getZone());
        System.out.println(TimeZone.getDefault());

        // 获取时区
        Clock clock1 = Clock.systemDefaultZone();
        System.out.println(clock1.getZone());
        System.out.println(ZoneId.systemDefault());

        // 获取当前时间
        System.out.println(clock1.instant());
        System.out.println(new Date());

        // 获取毫秒数
        System.out.println(clock.millis());
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 获取当前日期
     */
    public static void localDate() {

        LocalDate now = LocalDate.now();
        LocalDate now1 = LocalDate.now(Clock.systemUTC());

        System.out.println(now);
        System.out.println(now1);

        LocalDate of = LocalDate.of(2020, 3, 4);
        // 严格按照ISO yyyy-MM-dd验证，03写成3都不行
        LocalDate parse = LocalDate.parse("2020-03-04");
        System.out.println(of);
        System.out.println(parse);

        System.out.println("**************now****************");

        // 当前开始时间
        System.out.println("当前开始时间: " + now.atStartOfDay());
        // 当月第一天日期
        System.out.println("当月第一天日期: " + now.with(TemporalAdjusters.firstDayOfMonth()));
        // 本月第二天日期
        System.out.println("本月第二天日期: " + now.withDayOfMonth(2));
        // 当月最后一天
        System.out.println("当月最后一天: " + now.with(TemporalAdjusters.lastDayOfMonth()));
        // 今天是本月的第几天
        System.out.println("今天是本月的第几天: " + now.getDayOfMonth());
        // 明天
        System.out.println("明天: " + now.plusDays(1));
        // 昨天
        System.out.println("昨天: " + now.minusDays(1));
        // 今天是周几
        System.out.println("今天是周几: " + now.getDayOfWeek());
        // 下周的今天
        System.out.println("下周的今天: " + now.plusWeeks(1));
        // 上周的今天
        System.out.println("上周的今天: " + now.minusWeeks(1));
        // 本月的英文和数字月份
        System.out.println("本月的英文和数字月份: " + now.getMonth() + "-" + now.getMonthValue());
        // 下一个月的今天
        System.out.println("下一个月的今天: " + now.plusMonths(1));
        // 上一个月的今天
        System.out.println("上一个月的今天: " + now.minusMonths(1));

        //时间比较
        System.out.println(now.isEqual(LocalDate.of(2020, 03, 04)));
    }


    /**
     * 获取当前时间
     */
    public static void localTime() {

        LocalTime now = LocalTime.now();
        // 指定时区
        LocalTime now1 = LocalTime.now(Clock.system(ZoneId.systemDefault()));
        LocalTime now2 = LocalTime.now(Clock.systemUTC());

        System.out.println("不指定时区: "+ now);
        System.out.println("指定为系统默认时区: " + now1);
        System.out.println("指定为系统UTC时间: " + now2);

        System.out.println("************now************");
        // 清除毫秒位
        System.out.println("当前时间，清除毫秒位: " + now.withNano(0));

        // 获取当前的小时
        System.out.println("获取当前的小时: " + now.getHour());
        // 解析时间，时间也是按照ISO格式识别，但可以识别以下3种格式： 12:00 12:01:02 12:01:02.345
        System.out.println("时分格式: " + LocalTime.parse("18:51"));
        System.out.println("时分秒格式: " + LocalTime.parse("18:51:02"));
        System.out.println("时分秒毫秒格式: " + LocalTime.parse("18:51:02.345"));

        // 时间比较
        LocalTime other = LocalTime.of(18, 51, 02);
        System.out.println("在18:51:02之前？" + now.isBefore(other));
        System.out.println("在18:51:02之后？" + now.isAfter(other));
    }

    /**
     * 获取当前日期和时间
     */
    public static void localDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now(Clock.system(ZoneId.systemDefault()));
        LocalDateTime now2 = LocalDateTime.now(Clock.systemUTC());

        System.out.println("不指定时区：" + now);
        System.out.println("指定系统默认时区：" + now1);
        // 会少8小时
        System.out.println("指定系统UTC时区：" + now2);
    }

    /**
     * 特定时区的日期/时间
     */
    public static void zoneDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime now1 = ZonedDateTime.now(Clock.systemUTC());
        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println("不指定特定时区的日期和时间: " + now);
        System.out.println("指定为系统UTC时区的日期和时间: " + now1);
        System.out.println("指定为美国洛杉矶时区的日期和时间" + now2);
    }

    /**
     * 秒与纳秒级别上的一段时间
     */
    public static void duration() {

        LocalDateTime from = LocalDateTime.of(2019, Month.OCTOBER, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2020, Month.MARCH, 1, 23, 59, 59);

        Duration between = Duration.between(from, to);
        System.out.println(between.toDays());
        System.out.println(between.toHours());
    }

    /**
     * 日期格式
     */
    public static void date2Str() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(DateTimeFormatter.ofPattern(PATTERN_1)));
        System.out.println(now.format(DateTimeFormatter.ofPattern(PATTERN_2)));
        System.out.println(now.format(DateTimeFormatter.ofPattern(PATTERN_3)));
    }

    /**
     * 日期字符串转为LocalDateTime格式
     */
    public static void str2Date() {

        String dateStr = "2020-03-04";
        LocalDate parse = LocalDate.parse(dateStr);
        System.out.println(parse);
        LocalDate parse3 = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(PATTERN_1));
        System.out.println(parse3);

        String dateStr2 = "2020-03-04 19:08:45";
        LocalDateTime parse2 = LocalDateTime.parse(dateStr2, pattern(PATTERN_3));
        System.out.println(parse2);

    }

    public static DateTimeFormatter pattern(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }


    /**
     * Date转换为LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return localDateTime;
    }

    /**
     * LocalDateTime转换为Date
     */
    public static Date localDateTime2Date() {
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        Date date = Date.from(instant);

        return date;
    }

    /**
     * LocalDateTime转换为LocalDate
     */
    public static LocalDate localDateTime2LocalDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate localDate = now.toLocalDate();

        return localDate;
    }

    /**
     * LocalDateTime转换为LocalTime
     */
    public static LocalTime localDateTime2LocalTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime LocalTime = now.toLocalTime();

        return LocalTime;
    }

    /**
     * LocalDate转换为Date
     */
    public static Date localDate2Date() {
        LocalDate now = LocalDate.now();
        Instant instant = now.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        Date date = Date.from(instant);

        return date;
    }
}
