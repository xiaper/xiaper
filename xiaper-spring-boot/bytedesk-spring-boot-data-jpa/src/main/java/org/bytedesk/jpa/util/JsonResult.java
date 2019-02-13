package org.bytedesk.jpa.util;

/**
 * 返回结果
 *
 * @author bytedesk.com
 * @param <T>
 */
public class JsonResult<T> {

    private String message;

    private int status_code;

    private T data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
