package cn.tedu.csmall.common.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {

    // 状态码，例如：200
    private Integer state;

    // 消息，例如："登录失败，用户名不存在"
    private String message;

    // 数据
    private T data;

    private JsonResult() {
    }

    public static JsonResult<Void> ok() {
        // JsonResult jsonResult = new JsonResult();
        // jsonResult.setState(1);
        // return jsonResult;
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setState(State.OK.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult<Void> fail(State state, String message) {
        JsonResult<Void> jsonResult = new JsonResult<>();
        jsonResult.setState(state.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
