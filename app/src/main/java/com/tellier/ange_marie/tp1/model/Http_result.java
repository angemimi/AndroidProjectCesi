package com.tellier.ange_marie.tp1.model;

/**
 * Created by ange-marie on 25/10/17.
 */

public class Http_result {
    public final int status;
    public final String json;

    public Http_result(int status, String json){
        this.status = status;
        this.json = json;
    }
}
