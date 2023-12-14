package spring.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//指定ComponentScan注解可以修饰Type程序元素
@Retention(RetentionPolicy.RUNTIME)//指定该注解作用范围/时间
public @interface ComponentScan {
    String value() default ""; //表示该注解可以传入value属性
}
