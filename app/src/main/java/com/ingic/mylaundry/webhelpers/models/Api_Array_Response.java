package com.ingic.mylaundry.webhelpers.models;

import java.util.ArrayList;

/**
 * Created by ahsanali on 9/6/2017.
 */
public class Api_Array_Response<T> {

    private String Message;
    private String Response;
    private ArrayList<T> Result;
    private int UserBlocked;


    public int getUserBlocked() {
        return UserBlocked;
    }

    public void setUserBlocked(int userBlocked) {
        UserBlocked = userBlocked;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        if (!message.equals(null))
            Message = message;
        else
            Message = "Something went wrong";
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }


    public ArrayList<T> getResult() {
        return Result;
    }

    public void setResult(ArrayList<T> result) {
        Result = result;
    }

}
