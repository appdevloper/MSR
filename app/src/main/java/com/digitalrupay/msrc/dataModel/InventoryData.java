package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/9/2017.
 */

public class InventoryData  implements Serializable {
    @SerializedName("dealer_outward_id")
    private String dealer_outward_id;
    @SerializedName("inv_id")
    private String inv_id;
    @SerializedName("tot_emp_inv")
    private String tot_emp_inv;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("name")
    private String name;
    @SerializedName("item_number")
    private String item_number;
    @SerializedName("item_price")
    private String item_price;

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

    public String getdealer_outward_id() {
        return dealer_outward_id;
    }
    public void setdealer_outward_id(String dealer_outward_id) {
        this.dealer_outward_id = dealer_outward_id;
    }

    public String getinv_id() {
        return inv_id;
    }
    public void setinv_id(String inv_id) {
        this.inv_id = inv_id;
    }

    public String gettot_emp_inv() {
        return tot_emp_inv;
    }
    public void settot_emp_inv(String tot_emp_inv) {
        this.tot_emp_inv = tot_emp_inv;
    }

    public String getdateCreated() {
        return dateCreated;
    }
    public void setdateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getemp_id() {
        return emp_id;
    }
    public void setemp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
