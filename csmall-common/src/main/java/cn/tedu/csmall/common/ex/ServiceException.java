package cn.tedu.csmall.common.ex;

import cn.tedu.csmall.common.web.State;

public class ServiceException extends RuntimeException {
    private State state;

    public ServiceException(State state, String message) {
        super(message);
        if (state == null) {
            throw new IllegalArgumentException("使用ServiceException必须指定错误时的业务状态码！");
        }
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
