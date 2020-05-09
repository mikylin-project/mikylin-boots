package cn.mikylin.boot.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 接口 advice （增强类）
 *
 * @author mikylin
 * @date 20200506
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdviceHandlers {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException e) {
        log.error("接口异常，错误信息为：{}", e.getMessage());
        return "请求出现异常，请联系开发";
    }


}
