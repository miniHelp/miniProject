package onlineModel;

import java.sql.Date;

public class MerchantLogVO {

    private Integer merchant_log_id;
    private String kind;
    private String msg;
    private Date create_date;
    private Integer user_id;
    private String user_login_id;
    private String user_name;
    private String user_ip;
    private String user_ip_area;

    public Integer getMerchant_log_id() {
        return merchant_log_id;
    }

    public void setMerchant_log_id(Integer merchant_log_id) {
        this.merchant_log_id = merchant_log_id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_login_id() {
        return user_login_id;
    }

    public void setUser_login_id(String user_login_id) {
        this.user_login_id = user_login_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getUser_ip_area() {
        return user_ip_area;
    }

    public void setUser_ip_area(String user_ip_area) {
        this.user_ip_area = user_ip_area;
    }
}
