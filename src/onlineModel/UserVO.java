package onlineModel;

import java.io.Serializable;
import java.sql.Date;

public class UserVO implements Serializable {

    private Integer user_id;
    private String user_name;
    private String user_descr;
    private String login_id;
    private String login_pwd;
    private String email;
    private String icon_path;
    private String ip_address;
    private String ip_area;
    private Date last_access_date;
    private String user_status;
    private String roles;
    private Date create_date;
    private Date update;
    private String user_group;
    private String can_change_unpaid_status;
    private String change_unpaid_pwd;
    private String can_change_wait_confirm_status;
    private String change_wait_confirm_pwd;
    private Integer wait_confirm_pwd_fee_duration;
    private Integer pwd_error_times;
    private String can_change_name;
    private String change_name_pwd;
    private Integer change_name_fee_duration;
    private Integer unpaid_fee_duration;


    public Integer getUnpaid_fee_duration() {
        return unpaid_fee_duration;
    }

    public void setUnpaid_fee_duration(Integer unpaid_fee_duration) {
        this.unpaid_fee_duration = unpaid_fee_duration;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_descr() {
        return user_descr;
    }

    public void setUser_descr(String user_descr) {
        this.user_descr = user_descr;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getIp_area() {
        return ip_area;
    }

    public void setIp_area(String ip_area) {
        this.ip_area = ip_area;
    }

    public Date getLast_access_date() {
        return last_access_date;
    }

    public void setLast_access_date(Date last_access_date) {
        this.last_access_date = last_access_date;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public String getCan_change_unpaid_status() {
        return can_change_unpaid_status;
    }

    public void setCan_change_unpaid_status(String can_change_unpaid_status) {
        this.can_change_unpaid_status = can_change_unpaid_status;
    }

    public String getChange_unpaid_pwd() {
        return change_unpaid_pwd;
    }

    public void setChange_unpaid_pwd(String change_unpaid_pwd) {
        this.change_unpaid_pwd = change_unpaid_pwd;
    }

    public String getCan_change_wait_confirm_status() {
        return can_change_wait_confirm_status;
    }

    public void setCan_change_wait_confirm_status(String can_change_wait_confirm_status) {
        this.can_change_wait_confirm_status = can_change_wait_confirm_status;
    }

    public String getChange_wait_confirm_pwd() {
        return change_wait_confirm_pwd;
    }

    public void setChange_wait_confirm_pwd(String change_wait_confirm_pwd) {
        this.change_wait_confirm_pwd = change_wait_confirm_pwd;
    }

    public Integer getWait_confirm_pwd_fee_duration() {
        return wait_confirm_pwd_fee_duration;
    }

    public void setWait_confirm_pwd_fee_duration(Integer wait_confirm_pwd_fee_duration) {
        this.wait_confirm_pwd_fee_duration = wait_confirm_pwd_fee_duration;
    }

    public Integer getPwd_error_times() {
        return pwd_error_times;
    }

    public void setPwd_error_times(Integer pwd_error_times) {
        this.pwd_error_times = pwd_error_times;
    }

    public String getCan_change_name() {
        return can_change_name;
    }

    public void setCan_change_name(String can_change_name) {
        this.can_change_name = can_change_name;
    }

    public String getChange_name_pwd() {
        return change_name_pwd;
    }

    public void setChange_name_pwd(String change_name_pwd) {
        this.change_name_pwd = change_name_pwd;
    }

    public Integer getChange_name_fee_duration() {
        return change_name_fee_duration;
    }

    public void setChange_name_fee_duration(Integer change_name_fee_duration) {
        this.change_name_fee_duration = change_name_fee_duration;
    }
}
