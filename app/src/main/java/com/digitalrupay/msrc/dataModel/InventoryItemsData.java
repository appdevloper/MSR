package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/11/2017.
 */

public class InventoryItemsData implements Serializable {
    @SerializedName("inv_id")
    private String inv_id;
    @SerializedName("name")
    private String name;
    @SerializedName("item_number")
    private String item_number;
    @SerializedName("item_price")
    private String item_price;
    @SerializedName("dateCreated")
    private String dateCreated;

    public String getinv_id() {
        return inv_id;
    }
    public void setinv_id(String inv_id) {
        this.inv_id = inv_id;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public String getitem_number() {
        return item_number;
    }
    public void setitem_number(String item_number) {
        this.item_number = item_number;
    }

    public String getitem_price() {
        return item_price;
    }
    public void setitem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getdateCreated() {
        return dateCreated;
    }
    public void setdateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}