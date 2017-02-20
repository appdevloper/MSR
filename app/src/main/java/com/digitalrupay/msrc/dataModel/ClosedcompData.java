package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/20/2017.
 */

public class ClosedcompData implements Serializable {
    @SerializedName("closed_id")
    private String closed_id;
    @SerializedName("closed_category")
    private String closed_category;

    public String getclosed_id() {
        return closed_id;
    }
    public void setclosed_id(String closed_id) {
        this.closed_id = closed_id;
    }

    public String getclosed_category() {
        return closed_category;
    }
    public void setclosed_category(String closed_category) {
        this.closed_category = closed_category;
    }
}
