package zcy.developer.scorpiosdk.net.factory.entity;

/**
 * @author zcy.
 * @date 2017/7/27.
 */

public class CommonBean {

    /**
     * data : 保存成功
     * status : 200
     * code : 0
     * message : 返回内容正常
     */

    private String data;
    private int status;
    private int code;
    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
