package com.chang.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        // TODO 集合的非空判断
        int[] value = constraintAnnotation.values();
        for (int i : value) {
            set.add(i);
        }
    }

    /***
     * @description 判断是否校验成功
     * @param value 需要校验的值,我们提交的值
     * @param context
     * @return boolean
     * @author chang
     * @date 2023/1/31 21:00
    */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
