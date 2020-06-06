# jdbc-template-extend 基于 spring jdbcTemplate 做的升级版

增加依赖
```
<!-- jdbc orm -->
<dependency>
  <groupId>com.jack</groupId>
  <artifactId>jdbc-template-extend</artifactId>
  <version>1.1</version>
</dependency>
```

# 比jdbctemplate有哪些优势
- 重新定义了 ExtendJdbcTemplate 类，集成自JdbcTemplate
- 没有改变原始JdbcTemplate的功能
- 增加了orm框架必备的操作对象来管理数据
- 简单的数据库操作使用 ExtendJdbcTemplate 提高效率
- 支持分布式主键ID的自动生成

# 用法如下
定义数据表对应的PO类,表名和字段名以注解中的value为准
```
package com.jack.entity;

import com.jack.jdbc.Orders;
import com.jack.jdbc.annotation.Field;
import com.jack.jdbc.annotation.TableName;

import java.io.Serializable;

@TableName(value="student", desc="学生表", author="jack")
public class Student implements Serializable {

    private static final long serialVersionUID = -6690784263770712827L;

    @Field(value="id", desc="主键ID")
    private String id;

    @Field(value="name", desc="学生名称")
    private String name;

    @Field(value="city", desc="所在城市")
    private String city;

    @Field(value="region", desc="所在区域")
    private String region;

    @Field(value="ld_num", desc="小区楼栋号")
    private String ldNum;

    @Field(value="unit_num", desc="楼栋单元号")
    private String unitNum;

    public Student() {
        super();
    }

    // 需要显示的字段名称，没有传入的字段则值为null
    public final static String[] SHOW_FIELDS = new String[]{ "city", "region", "name", "ld_num" };

    // 需要传入的字段名称，和传入的参数顺序相对应
    public final static String[] QUERY_FIELDS = new String[]{ "city", "region", "name" };

    public final static Orders[] ORDER_FIELDS = new Orders[] { new Orders("id", Orders.OrderType.ASC) };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLdNum() {
        return ldNum;
    }

    public void setLdNum(String ldNum) {
        this.ldNum = ldNum;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }
}


```

```
@Service
public class LdServiceImpl extends EntityService<LouDong> implements LdService {

	public long count() {
		return super.count();
	}

	public List<LouDong> findAll() {
		return super.list(LouDong.ORDER_FIELDS);
	}

	public List<LouDong> find(String city) {
		return super.list("city", city);
	}

	public List<LouDong> find(String city, String region) {
		return super.list(new String[]{"city", "region"}, new Object[] {city, region});
	}

	public List<LouDong> find(String city, String region, String name) {
		return super.list(LouDong.SHOW_FIELDS, LouDong.QUERRY_FIELDS, new Object[] {city, region, name});
	}

	public List<LouDong> findAll(PageQueryParam page) {
		return super.listForPage(page.getStart(), page.getLimit(), LouDong.ORDER_FIELDS);
	}

	public boolean exists(String city) {
		return super.exists("city", city);
	}

	public List<LouDong> in(String[] names) {
		return super.in(new String[]{"city", "region"}, "name", names);
	}

	public List<LouDongDo> group() {
		return super.getJdbcTemplate().list(LouDongDo.class, "select city,count(id) as count from loudong GROUP BY city");
	}

	public LouDong get(String id) {
		return super.getById("id", id);
	}

	@Transactional
	public void delete(String name) {
		super.deleteById("name", name);
	}

	public void save(LouDong louDong) {
		super.save(louDong);
	}

	@Override
	public void saveList(List<LouDong> list) {
		super.batchSave(list);
	}

	@Override
	public void update(LouDong louDong) {
		super.update(louDong, "id");
	}

	@Override
	public void updateList(List<LouDong> list) {
		super.batchUpdateByContainsFields(list, "id", "city");
	}

}
```

# 如何使用
## 定义接口
```
package com.jack.service;

import com.jack.entity.Student;
import com.jack.jdbc.PageQueryParam;

import java.util.List;

/**
 * 学生业务接口类
 */
public interface StudentService {
    long count();

    List<Student> findAll();

    List<Student> find(String city);

    List<Student> find(String city, String region);

    List<Student> find(String city, String region, String name);

    List<Student> findAll(PageQueryParam page);

    boolean exists(String city);

    List<Student> in(String[] names);

    Student get(String id);

    void delete(String name);

    void save(Student student);

    void saveList(List<Student> list);

    void update(Student student);

    void updateList(List<Student> list);
}

```
## 定义接口实现
```
package com.jack.service.impl;

import com.jack.entity.Student;
import com.jack.jdbc.EntityService;
import com.jack.jdbc.PageQueryParam;
import com.jack.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl extends EntityService<Student> implements StudentService {

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public List<Student> findAll() {
        return super.list(Student.ORDER_FIELDS);
    }

    @Override
    public List<Student> find(String city) {
        return super.list("city", city);
    }

    @Override
    public List<Student> find(String city, String region) {
        return super.list(new String[]{"city", "region"}, new Object[]{city, region});
    }

    @Override
    public List<Student> find(String city, String region, String name) {
        return super.list(Student.SHOW_FIELDS, Student.QUERY_FIELDS, new Object[] {city, region, name});
    }

    @Override
    public List<Student> findAll(PageQueryParam page) {
        return super.listForPage(page.getStart(), page.getLimit(), Student.ORDER_FIELDS);
    }

    @Override
    public boolean exists(String city) {
        return super.exists("city", city);
    }

    @Override
    public List<Student> in(String[] names) {
        return super.in(new String[] {"city", "region"}, "name", names);
    }

    @Override
    public Student get(String id) {
        return super.getById("id", id);
    }

    @Transactional
    @Override
    public void delete(String name) {
        super.deleteById("name", name);
    }

    @Override
    public void save(Student student) {
        super.save(student);
    }

    @Override
    public void saveList(List<Student> list) {
        super.batchSave(list);
    }

    @Override
    public void update(Student student) {
        super.update(student, "id");
    }

    @Override
    public void updateList(List<Student> list) {
        super.batchUpdate(list, "id", "city");
    }
}

```

# FAQ
## 项目中怎么配置使用呢？
首先你得引入jdbcTemplate的包，然后再配置jdbc-tempate-extend的包
如果是spring boot项目可以使用bean的方式配置
```
package com.jack.config;

import com.jack.jdbc.ExtendJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class BeanConfig {
    /**
     * 如果是spring boot项目可以使用bean的方式配置
     */
    @Bean(autowire = Autowire.BY_NAME)
    public ExtendJdbcTemplate extendJdbcTemplate() {
        // ExtendJdbcTemplate构造方法中传的com.jack.entity是你数据表对应的PO实体类所在的包路径
        // 推荐放一个包下，如果在多个包下可以配置多个包的路径
        return new ExtendJdbcTemplate("com.jack.entity");
    }
}

```
上面构造方法中传的com.jack.entity是你数据表对应的PO实体类所在的包路径，推荐放一个包下，如果在多个包下可以配置多个包的路径
```
package com.jack.config;

import com.jack.jdbc.ExtendJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class BeanConfig {
    /**
     * 如果是spring boot项目可以使用bean的方式配置
     */
    @Bean(autowire = Autowire.BY_NAME)
    public ExtendJdbcTemplate extendJdbcTemplate() {
        // ExtendJdbcTemplate构造方法中传的com.jack.entity是你数据表对应的PO实体类所在的包路径
        // 推荐放一个包下，如果在多个包下可以配置多个包的路径
        return new ExtendJdbcTemplate("com.jack.entity", "com.jack.mongodb.po");
    }
}


```
如果是用xml的方式，那就用&lt;bean&gt;标签配置即可。
```
<!-- 增强版JdbcTemplate -->
<bean id="extendJdbcTemplate" class="com.jack.jdbc.ExtendJdbcTemplate">
   <property name="dataSource" ref="dataSource"/>
   <constructor-arg>
      <array>
         <value>com.jack.entity</value>
         <value>com.jack.mongodb.po</value>
      </array>
   </constructor-arg>
</bean>
```

> 注意：在配置 ExtendJdbcTemplate 的时候也可以不用传入对应的包信息，如果没有传入包信息，jdbc-template-extend 在使用的时候会根据查询传入的实体类动态获取映射信息，推荐大家配置时传入包信息。

## 除了继承EntityService还能用什么办法使用？
大家完全可以直接注入JdbcTemplate来操作数据库，我这里只是对JdbcTemplate进行了扩展

当然也可以直接注入扩展之后的 ExtendJdbcTemplate 来操作

```
@Autowired
private ExtendJdbcTemplate extendJdbcTemplate;
```
## 支持分布式主键ID的自动生成怎么使用？
只需要在对应的注解字段上加上@AutoId注解即可，注意此字段的类型必须为String或者Long, 需要关闭数据库的自增功能,ID算法用的是[ShardingJdbc](http://shardingjdbc.io/)中的ID算法，在分布式环境下并发会出现id相同的问题，需要为每个节点配置不同的wordid即可，通过-Dsharding-jdbc.default.key.generator.worker.id=wordid设置
```
@AutoId
@Field(value="id", desc="主键ID")
private String id;
```
## 不用注解做字段名映射怎么使用？
通过@Field注解方式可以允许数据库中的字段名称跟实体类的名称不一致，通过注解的方式来映射，如果你觉得太麻烦了，那么你可以按下面的方式使用：

```
CREATE TABLE `Order`(
  id bigint(64) not null,
  shopName varchar(20) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

实体类定义,只需要类名跟表名一致，属性名和字段名一致：

```
public class Order {
    private Long id;
    private String shopName;
    // get set...
}
```
## 连表查询的结果如何定义对应的实体类？
sql语句：select tab1.name,tab2.shop_name from tab1,tab2

查询出的结果肯定是name,shop_name 2个字段，这种你可以直接定义一个类，然后写上这2个字段对应的属性即可，这边有下划线定义的字段，所以我们在实体类中需要用注解来映射


```
public class Order {
    private Long name;
    @Field(value="shop_name", desc="商品名称")
    private String shopName;
    // get set...
}
```
如果不想使用注解那就在sql语句中为字段添加别名：select tab1.name,tab2.shop_name as shopName from tab1,tab2

```
public class Order {
    private Long name;
    private String shopName;
    // get set...
}
```

## 为何要封装？
有很多人问为什么要封装一个，为什么不直接用jpa或者mybatis,这个问题我是这么看的，框架这东西很多，每个人可以根据自己的喜好来使用，可以用开源的，也可以自己封装，其实我这也不算重复造轮子，因为JdbcTemplate已经封装了很好用了，我只是在上面做了一些小小的扩展而已，也没有说要去跟mybatis这些框架去做比较，我个人就是喜欢直接在代码中写SQL,JdbcTemplate符合我的开发风格，就这么简单。

## 如何快速生成表对应的PO类？
当表比较多的时候，每个表都要对应一个实体类，手动去创建虽然简单，但是也耗费时间，在 ExtendJdbcTemplate 中提供了一个generatePoClass的方法，可以基于当前程序连接的数据库生成数据库中所有表的PO类，生成完成之后直接复制到项目中即可使用。大部分常用的类型生成应该是支持的，如果有些生成不了，自己可以手动改下。
```
super.getJdbcTemplate().generatePoClass("com.jack.entity", "jack", "F://generator/java");
```
- 第一个参数是PO类的包名
- 第二个参数是PO类的创建者
- 第三个参数是PO类保存的路径
