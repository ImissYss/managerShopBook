package com.maidat.mybooks.utils.dto;

import lombok.Getter;
import lombok.Setter;


public class Response {

    public final static String DONE = "done";
    public final static String ERROR = "error";

    private String status;
    private Object data;

    public Response(){
        status = "ERROR";
    }

    public Response(String status){
        this.status = status;
    }

    public Response(String status, Object data){
        this.status = status;
        this.data = data;
    }
    public void set(Object data){
        this.data = data;
        status = DONE;
    }
    public void set(String status, Object data) {
        this.data = data;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
