package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by sridhar on 2/10/2017.
 */

public class ComplaintData  implements Serializable{
    @SerializedName("cust_id")
    private String cust_id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("addr1")
    private String addr1;
    @SerializedName("addr2")
    private String addr2;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;
    @SerializedName("city")
    private String city;
    @SerializedName("mobile_no")
    private String mobile_no;
    @SerializedName("email_id")
    private String email_id;
    @SerializedName("custom_customer_no")
    private String custom_customer_no;
    @SerializedName("complaint_id")
    private String complaint_id;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("comp_ticketno")
    private String comp_ticketno;
    @SerializedName("comp_cat")
    private String comp_cat;
    @SerializedName("complaint")
    private String complaint;
    @SerializedName("comp_status")
    private String comp_status;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("created_date")
    private String created_date;
    @SerializedName("last_edited_by")
    private String last_edited_by;
    @SerializedName("comp_remarks")
    private String comp_remarks;
    @SerializedName("closed_img")
    private String closed_img;

    public String getcust_id() {
        return cust_id;
    }
    public void setcust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getfirst_name() {
        return first_name;
    }
    public void setfirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getlast_name() {
        return last_name;
    }
    public void setlast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getaddr1() {
        return addr1;
    }
    public void setaddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getaddr2() {
        return addr2;
    }
    public void setaddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getcountry() {
        return country;
    }
    public void setcountry(String country) {
        this.country = country;
    }

    public String getstate() {
        return state;
    }
    public void setstate(String state) {
        this.state = state;
    }

    public String getcity() {
        return city;
    }
    public void setcity(String city) {
        this.city = city;
    }

    public String getmobile_no() {
        return mobile_no;
    }
    public void setamobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getemail_id() {
        return email_id;
    }
    public void setemail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getcustom_customer_no() {
        return custom_customer_no;
    }
    public void setcustom_customer_no(String custom_customer_no) {
        this.custom_customer_no = custom_customer_no;
    }

    public String getcomplaint_id() {
        return complaint_id;
    }
    public void setcomplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getcustomer_id() {
        return customer_id;
    }
    public void setcustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getcomp_ticketno() {
        return comp_ticketno;
    }
    public void setcomp_ticketno(String customer_id) {
        this.comp_ticketno = comp_ticketno;
    }

    public String getcomp_cat() {
        return comp_cat;
    }
    public void setcomp_cat(String comp_cat) {
        this.comp_cat = comp_cat;
    }

    public String getcomplaint() {
        return complaint;
    }
    public void setcomplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getcomp_status() {
        return comp_status;
    }
    public void setcomp_status(String comp_status) {
        this.comp_status = comp_status;
    }

    public String getcreated_by() {
        return created_by;
    }
    public void setcreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getcreated_date() {
        return created_date;
    }
    public void setcreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getlast_edited_by() {
        return last_edited_by;
    }
    public void setlast_edited_by(String last_edited_by) {
        this.last_edited_by = last_edited_by;
    }

    public String getcomp_remarks() {
        return comp_remarks;
    }
    public void setcomp_remarks(String comp_remarks) {
        this.comp_remarks = comp_remarks;
    }

    public String getclosed_img() {
        return closed_img;
    }
    public void setclosed_img(String closed_img) {
        this.closed_img = closed_img;
    }
}
