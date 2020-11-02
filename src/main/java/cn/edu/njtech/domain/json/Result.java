package cn.edu.njtech.domain.json;

public class Result {
    private Boolean success;
    private String message;
    private int code;
    Object result;

    public Result() {
    }

    public Result(Boolean success, String message, int code, Object object) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.result = object;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
