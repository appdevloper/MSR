package com.digitalrupay.msrc.dataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 2/9/2017.
 */

public class OperatorLoginData implements Serializable {
    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("emp_first_name")
    private String emp_first_name;
    @SerializedName("emp_last_name")
    private String emp_last_name;
    @SerializedName("emp_add1")
    private String emp_add1;
    @SerializedName("emp_add2")
    private String emp_add2;
    @SerializedName("dealer_name")
    private String dealer_name;
    @SerializedName("emp_city")
    private String emp_city;
    @SerializedName("emp_pin_code")
    private String emp_pin_code;
    @SerializedName("emp_email")
    private String emp_email;
    @SerializedName("emp_phone_no")
    private String emp_phone_no;
    @SerializedName("emp_mobile_no")
    private String emp_mobile_no;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("user_role")
    private String user_role;
    @SerializedName("emp_password")
    private String emp_password;
    @SerializedName("date_created")
    private String date_created;
    @SerializedName("status")
    private String status;
    @SerializedName("inactive_date")
    private String inactive_date;

    public String getuser_type() {
        return user_type;
    }
    public void setuser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getemp_id() {
        return emp_id;
    }
    public void setemp_id(String emp_id) {
        this.emp_id = emp_id;
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

    public String getemp_add1() {
        return emp_add1;
    }
    public void setemp_add1(String emp_add1) {
        this.emp_add1 = emp_add1;
    }

    public String getemp_add2() {
        return emp_add2;
    }
    public void setemp_add2(String emp_add2) {
        this.emp_add2 = emp_add2;
    }

    public String getdealer_name() {
        return dealer_name;
    }
    public void setdealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getemp_city() {
        return emp_city;
    }
    public void setemp_city(String emp_city) {
        this.emp_city = emp_city;
    }


    public String getemp_pin_code() {
        return emp_pin_code;
    }
    public void setemp_pin_code(String emp_pin_code) {
        this.emp_pin_code = emp_pin_code;
    }

    public String getemp_email() {
        return emp_email;
    }
    public void setemp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getemp_phone_no() {
        return emp_email;
    }
    public void setemp_phone_no(String emp_phone_no) {
        this.emp_phone_no = emp_phone_no;
    }

    public String getemp_mobile_no() {
        return emp_mobile_no;
    }
    public void setemp_mobile_no(String emp_mobile_no) {
        this.emp_mobile_no = emp_mobile_no;
    }

    public String getuser_role() {
        return user_role;
    }
    public void setuser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getemp_password() {
        return emp_password;
    }
    public void setemp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getdate_created() {
        return date_created;
    }
    public void setdate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }

    public String getinactive_date() {
        return inactive_date;
    }
    public void setinactive_date(String inactive_date) {
        this.inactive_date = inactive_date;
    }
}
