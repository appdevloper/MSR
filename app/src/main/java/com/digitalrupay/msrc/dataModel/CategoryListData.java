package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/10/2017.
 */

public class CategoryListData  implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("category")
    private String category;

    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }

    public String getcategory() {
        return category;
    }
    public void setcategory(String category) {
        this.category = category;
    }
}
