package com.enchere.response;

public class ResponseData<Type> {
    private boolean isSuccess;
    private Type data;

    public ResponseData(boolean isSuccess, Type data){
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }
}
