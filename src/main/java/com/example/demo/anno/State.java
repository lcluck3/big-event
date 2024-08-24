package com.example.demo.anno;

import com.example.demo.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented//元注解
@Constraint(
        validatedBy = {StateValidation.class}//提供校验规则的类
)
@Target({ ElementType.FIELD})//元注解
@Retention(RetentionPolicy.RUNTIME)//元注解

public @interface State {
    //错误提示信息
    String message() default "This state can only be published(已发布) or draft(草稿)";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取state注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
