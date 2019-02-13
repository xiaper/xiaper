package org.bytedesk.jpa.util;

/**
 * LayIM 上传图片、文件返回结果格式
 *
 * @author bytedesk.com on 2019/1/8
 */
public class LayIMUploadJsonResult<T> {

    private String msg;

    private int code;

    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
