package github.fast.base;

public class ActionResult<T> {
    /**
     * 响应码
     */
    protected int code;

    /**
     * 响应消息
     */
    protected String message;

    /**
     * 响应对象
     */
    protected T value;

    public ActionResult() {
        code = 0;
        message = "成功";

    }

    public ActionResult(T value) {
        this.code = 0;
        this.message = "成功";
        this.value = value;
    }


    public ActionResult(int code, String message, T value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public ActionResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.value = null;
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

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}


