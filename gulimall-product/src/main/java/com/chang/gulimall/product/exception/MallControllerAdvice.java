package com.chang.gulimall.product.exception;

import com.chang.common.exception.BizCodeEnum;
import com.chang.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: MallControllerAdvice 统一异常处理器,集中处理所有的异常
 * @Author Chang
 * @Date: 2023/3/26 16:52
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.chang.gulimall.product.controller")
public class MallControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        // 获取bindingResult
        BindingResult result = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }));
        log.error("数据校验出现问题:{}, 异常的类型:{}", e.getMessage(), e.getClass());

        // 将所有的异常信息返回
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);

    }

    //处理全局异常
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        log.error("异常:{}",throwable);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
