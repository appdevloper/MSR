package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/8/2017.
 */

public class OperatorCode implements Serializable {
    @SerializedName("op_id")
    private String op_id;
    @SerializedName("op_code")
    private String op_code;
    @SerializedName("op_url")
    private String op_url;
    @SerializedName("op_busiName")
    private String op_busiName;
    @SerializedName("op_logo")
    private String op_logo;
    @SerializedName("op_dateCreated")
    private String op_dateCreated;

    public String getop_id() {
        return op_id;
    }
    public void setop_id(String op_id) {
        this.op_id = op_id;
    }
    public String getop_logo() {
        return op_logo;
    }
    public void setop_logo(String op_logo) {
        this.op_logo = op_logo;
    }

    public String getop_code() {
        return op_code;
    }
    public void setop_code(String op_code) {
        this.op_code = op_code;
    }

    public String getop_url() {
        return op_url;
    }
    public void setop_url(String op_url) {
        this.op_url = op_url;
    }

    public String getop_busiName() {
        return op_busiName;
    }
    public void setop_busiName(String op_busiName) {
        this.op_busiName = op_busiName;
    }

    public String getop_dateCreated() {
        return op_dateCreated;
    }
    public void setop_dateCreated(String op_dateCreated) {
        this.op_dateCreated = op_dateCreated;
    }
}
