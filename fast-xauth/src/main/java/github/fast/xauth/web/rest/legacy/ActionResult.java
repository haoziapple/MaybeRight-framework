package github.fast.xauth.web.rest.legacy;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-06 11:32
 */
public class ActionResult<T> {
    private int code;

    private String message;

    private T value;

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

    @Override
    public String toString() {
        return "ActionResult{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", value=" + value +
            '}';
    }
}
