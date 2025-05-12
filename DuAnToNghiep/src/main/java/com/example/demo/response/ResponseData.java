package com.example.demo.response;

import java.util.List;

public class ResponseData<T> {
    private boolean success; // Trạng thái thành công hay không
    private String message;  // Thông điệp phản hồi
    private List<T> data;    // Dữ liệu trả về

    // Constructor không tham số
    public ResponseData() {
    }

    // Constructor có tham số
    public ResponseData(boolean success, String message, List<T> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Constructor tiện ích cho thành công
    public static <T> ResponseData<T> success(String message, List<T> data) {
        return new ResponseData<>(true, message, data);
    }

    // Constructor tiện ích cho thất bại
    public static <T> ResponseData<T> failure(String message) {
        return new ResponseData<>(false, message, null);
    }

    // Getter và Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

