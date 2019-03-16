package onlineModel;


import java.io.Serializable;
import java.sql.Date;


public class PlatformVO implements Serializable { //接口資料的bean

	private String platform_name;
	private String platform_descr;
    private String platform_url;
    private Integer platform_id;
    private String merchant_no_tips;
    private String merchant_pwd_name;
    private String merchant_pwd_tips;
    private String platform_no_name;
    private String platform_no_tips;
    private String rsa_merchant_private_key_tips;
    private String rsa_merchant_public_key_tips;
    private String rsa_server_public_key_tips;
    private String pfx_mer_private_key_tips;
    private String pfx_mer_public_key_tips;
    private String pfx_ser_public_key_tips;
    private String pfx_mer_private_key_pwd_tips;
    private String callback_file;
    private String callback_param;
    private String platform_status;
    private Date create_date;
    private Date update_date;
    private String need_platform_no;
    private String need_merchant_pwd;
    private String query_pay_result_url;
    private Date last_submit_order_date;
    private Date last_callback_date;
    private Integer success_rate;
    private Integer paid_order_num;
    private Integer total_order_num;
    private String is_guma;
    private String is_new_jc_setting;
    private String is_use_proxy;

    public String getPlatform_status() {
        return platform_status;
    }

    public void setPlatform_status(String platform_status) {
        this.platform_status = platform_status;
    }



    public String getNeed_merchant_pwd() {
        return need_merchant_pwd;
    }

    public void setNeed_merchant_pwd(String need_merchant_pwd) {
        this.need_merchant_pwd = need_merchant_pwd;
    }


    @Override
    public String toString() {
        return "PlatformVO{" +
                "platform_name='" + platform_name + '\'' +
                ", platform_url='" + platform_url + '\'' +
                ", platform_id='" + platform_id + '\'' +
                ", merchant_no_tips='" + merchant_no_tips + '\'' +
                ", merchant_pwd_name='" + merchant_pwd_name + '\'' +
                ", merchant_pwd_tips='" + merchant_pwd_tips + '\'' +
                ", platform_no_name='" + platform_no_name + '\'' +
                ", platform_no_tips='" + platform_no_tips + '\'' +
                ", rsa_merchant_private_key_tips='" + rsa_merchant_private_key_tips + '\'' +
                ", rsa_server_public_key_tips='" + rsa_server_public_key_tips + '\'' +
                '}';
    }

    public String getMerchant_no_tips() {
        return merchant_no_tips;
    }

    public void setMerchant_no_tips(String merchant_no_tips) {
        this.merchant_no_tips = merchant_no_tips;
    }

    public String getMerchant_pwd_name() {
        return merchant_pwd_name;
    }

    public String getPlatform_descr() {
        return platform_descr;
    }

    public void setPlatform_descr(String platform_descr) {
        this.platform_descr = platform_descr;
    }

    public String getRsa_merchant_public_key_tips() {
        return rsa_merchant_public_key_tips;
    }

    public void setRsa_merchant_public_key_tips(String rsa_merchant_public_key_tips) {
        this.rsa_merchant_public_key_tips = rsa_merchant_public_key_tips;
    }

    public String getCallback_file() {
        return callback_file;
    }

    public void setCallback_file(String callback_file) {
        this.callback_file = callback_file;
    }

    public String getCallback_param() {
        return callback_param;
    }

    public void setCallback_param(String callback_param) {
        this.callback_param = callback_param;
    }


    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getNeed_platform_no() {
        return need_platform_no;
    }

    public void setNeed_platform_no(String need_platform_no) {
        this.need_platform_no = need_platform_no;
    }

    public String getPfx_mer_private_key_tips() {
        return pfx_mer_private_key_tips;
    }

    public void setPfx_mer_private_key_tips(String pfx_mer_private_key_tips) {
        this.pfx_mer_private_key_tips = pfx_mer_private_key_tips;
    }

    public String getPfx_mer_public_key_tips() {
        return pfx_mer_public_key_tips;
    }

    public void setPfx_mer_public_key_tips(String pfx_mer_public_key_tips) {
        this.pfx_mer_public_key_tips = pfx_mer_public_key_tips;
    }

    public String getPfx_ser_public_key_tips() {
        return pfx_ser_public_key_tips;
    }

    public void setPfx_ser_public_key_tips(String pfx_ser_public_key_tips) {
        this.pfx_ser_public_key_tips = pfx_ser_public_key_tips;
    }

    public String getPfx_mer_private_key_pwd_tips() {
        return pfx_mer_private_key_pwd_tips;
    }

    public void setPfx_mer_private_key_pwd_tips(String pfx_mer_private_key_pwd_tips) {
        this.pfx_mer_private_key_pwd_tips = pfx_mer_private_key_pwd_tips;
    }

    public String getQuery_pay_result_url() {
        return query_pay_result_url;
    }

    public void setQuery_pay_result_url(String query_pay_result_url) {
        this.query_pay_result_url = query_pay_result_url;
    }

    public Date getLast_submit_order_date() {
        return last_submit_order_date;
    }

    public void setLast_submit_order_date(Date last_submit_order_date) {
        this.last_submit_order_date = last_submit_order_date;
    }

    public Date getLast_callback_date() {
        return last_callback_date;
    }

    public void setLast_callback_date(Date last_callback_date) {
        this.last_callback_date = last_callback_date;
    }

    public Integer getSuccess_rate() {
        return success_rate;
    }

    public void setSuccess_rate(Integer success_rate) {
        this.success_rate = success_rate;
    }

    public Integer getPaid_order_num() {
        return paid_order_num;
    }

    public void setPaid_order_num(Integer paid_order_num) {
        this.paid_order_num = paid_order_num;
    }

    public Integer getTotal_order_num() {
        return total_order_num;
    }

    public void setTotal_order_num(Integer total_order_num) {
        this.total_order_num = total_order_num;
    }

    public String getIs_guma() {
        return is_guma;
    }

    public void setIs_guma(String is_guma) {
        this.is_guma = is_guma;
    }

    public String getIs_new_jc_setting() {
        return is_new_jc_setting;
    }

    public void setIs_new_jc_setting(String is_new_jc_setting) {
        this.is_new_jc_setting = is_new_jc_setting;
    }

    public String getIs_use_proxy() {
        return is_use_proxy;
    }

    public void setIs_use_proxy(String is_use_proxy) {
        this.is_use_proxy = is_use_proxy;
    }

    public void setMerchant_pwd_name(String merchant_pwd_name) {
        this.merchant_pwd_name = merchant_pwd_name;
    }

    public String getMerchant_pwd_tips() {
        return merchant_pwd_tips;
    }

    public void setMerchant_pwd_tips(String merchant_pwd_tips) {
        this.merchant_pwd_tips = merchant_pwd_tips;
    }

    public String getPlatform_no_name() {
        return platform_no_name;
    }

    public void setPlatform_no_name(String platform_no_name) {
        this.platform_no_name = platform_no_name;
    }

    public String getPlatform_no_tips() {
        return platform_no_tips;
    }

    public void setPlatform_no_tips(String platform_no_tips) {
        this.platform_no_tips = platform_no_tips;
    }

    public String getRsa_merchant_private_key_tips() {
        return rsa_merchant_private_key_tips;
    }

    public void setRsa_merchant_private_key_tips(String rsa_merchant_private_key_tips) {
        this.rsa_merchant_private_key_tips = rsa_merchant_private_key_tips;
    }

    public String getRsa_server_public_key_tips() {
        return rsa_server_public_key_tips;
    }

    public void setRsa_server_public_key_tips(String rsa_server_public_key_tips) {
        this.rsa_server_public_key_tips = rsa_server_public_key_tips;
    }


    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }

    public String getPlatform_url() {
        return platform_url;
    }

    public void setPlatform_url(String platform_url) {
        this.platform_url = platform_url;
    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }
}
