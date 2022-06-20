package cn.tedu.csmall.common.ex.handler;

import cn.tedu.csmall.common.ex.ServiceException;
import cn.tedu.csmall.common.web.JsonResult;
import cn.tedu.csmall.common.web.State;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handlerServiceException(ServiceException ex) {
        return JsonResult.fail(ex.getState(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public JsonResult<Void> handlerBindException(BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            stringBuilder.append("；");
            stringBuilder.append(fieldError.getDefaultMessage());
        }
        String message = stringBuilder.substring(1);
        return JsonResult.fail(State.ERR_BAD_REQUEST, message);
    }

}
