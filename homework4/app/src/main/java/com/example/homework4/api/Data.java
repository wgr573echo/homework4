package com.example.homework4.api;

/**
 * author:apple
 * time:2021/11/19
 * version:1.0
 */

import java.util.List;

/**
 * Copyright 2021 bejson.com
 */

public class Data {

    private String from;
    private String to;
    private List<Trans_result> trans_result;
    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getTo() {
        return to;
    }

    public void setTrans_result(List<Trans_result> trans_result) {
        this.trans_result = trans_result;
    }
    public List<Trans_result> getTrans_result() {
        return trans_result;
    }

}
