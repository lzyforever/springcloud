package com.jack.jdbc.annotation;

import java.lang.annotation.*;

/**
 * 表名
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {
	
	/**
	 * 表名
	 */
	String value();
	
	/**
	 * 表描述
	 */
	String desc();
	
	/**
	 * 作者
	 */
	String author();
}
