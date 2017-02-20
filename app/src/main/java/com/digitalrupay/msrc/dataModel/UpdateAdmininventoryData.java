package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/14/2017.
 */

public class UpdateAdmininventoryData implements Serializable {

    @SerializedName("indent_id")
    private String indent_id;
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("inv_id")
    private String inv_id;
    @SerializedName("required_qty")
    private String required_qty;
    @SerializedName("ind_invoice_id")
    private String ind_invoice_id;
    @SerializedName("indent_reason")
    private String indent_reason;
    @SerializedName("required_date")
    private String required_date;
    @SerializedName("status")
    private String status;
    @SerializedName("shp_charges")
    private String shp_charges;
    @SerializedName("inward_remarks")
    private String inward_remarks;
    @SerializedName("emp_last_name")
    private String emp_last_name;
    @SerializedName("emp_first_name")
    private String emp_first_name;
    @SerializedName("updated_on")
    private String updated_on;
    @SerializedName("dateCreated")
    private String dateCreated;
    @SerializedName("name")
    private String name;
    @SerializedName("emp_mobile_no")
    private String emp_mobile_no;
    @SerializedName("item_number")
    private String item_number;
    @SerializedName("item_price")
    private String item_price;
    @SerializedName("approved_qty")
    private String approved_qty;

    public String getindent_id() {
        return indent_id;
    }
    public void setindent_id(String indent_id) {
        this.indent_id = indent_id;
    }

    public String getemp_id() {
        return emp_id;
    }
    public void setemp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getinv_id() {
        return inv_id;
    }
    public void setinv_id(String inv_id) {
        this.inv_id = inv_id;
    }


    public String getrequired_qty() {
        return required_qty;
    }
    public void setrequired_qty(String required_qty) {
        this.required_qty = required_qty;
    }

    public String getapproved_qty() {
        return approved_qty;
    }
    public void setapproved_qty(String approved_qty) {
        this.approved_qty = approved_qty;
    }

    public String getind_invoice_id() {
        return ind_invoice_id;
    }
    public void setind_invoice_id(String ind_invoice_id) {
        this.ind_invoice_id = ind_invoice_id;
    }

    public String getindent_reason() {
        return indent_reason;
    }
    public void setindent_reason(String indent_reason) {
        this.indent_reason = indent_reason;
    }

    public String getrequired_date() {
        return required_date;
    }
    public void setrequired_date(String required_date) {
        this.required_date = required_date;
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }

    public String getshp_charges() {
        return shp_charges;
    }
    public void setshp_charges(String shp_charges) {
        this.shp_charges = shp_charges;
    }

    public String getinward_remarks() {
        return inward_remarks;
    }
    public void setinward_remarks(String inward_remarks) {
        this.inward_remarks = inward_remarks;
    }

    public String getdateCreated() {
        return dateCreated;
    }
    public void setdateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getupdated_on() {
        return updated_on;
    }
    public void setupdated_on(String updated_on) {
        this.updated_on = updated_on;
    }


    public String getemp_first_name() {
        return emp_first_name;
    }
    public void setemp_first_name(String emp_first_name) {
        this.emp_first_name = emp_first_name;
    }

    public String getemp_last_name() {
        return emp_last_name;
    }
    public void setemp_last_name(String emp_last_name) {
        this.emp_last_name = emp_last_name;
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

}
