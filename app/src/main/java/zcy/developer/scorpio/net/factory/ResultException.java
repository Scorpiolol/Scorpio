package zcy.developer.scorpio.net.factory;


import java.io.IOException;

/**
 * Created by zcy on 2017/8/8.
 * 自定义异常类型
 * 用于解析json时，不同类型异常抛出
 */

public class ResultException extends IOException {

    private String errMsg;
    private int errCode;
    @SuppressWarnings("WeakerAccess")
    public ResultException(String errMsg, int errCode) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
