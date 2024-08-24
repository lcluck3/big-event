package com.example.demo.validation;

import com.example.demo.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//给哪一个注解提供
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @retur 如果返回F就是校验不通过,如果返回T就是校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if(value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    }
}
