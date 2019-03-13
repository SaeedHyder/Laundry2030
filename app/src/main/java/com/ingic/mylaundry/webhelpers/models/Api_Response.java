package com.ingic.mylaundry.webhelpers.models;

import org.json.JSONObject;

/**
 * Created by ahsanali on 9/5/2017.
 */

public class Api_Response<T> {


    private String Message;
    private Integer Code;
    private T Result;
    private Integer UserBlocked;
    private String pages;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public Integer getUserBlocked() {
        return UserBlocked;
    }

    public void setUserBlocked(Integer userBlocked) {
        UserBlocked = userBlocked;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
