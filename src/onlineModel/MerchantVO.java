package onlineModel;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class MerchantVO implements Serializable {

    private Integer merchantId;  //自动新增的商户流水号

    @Digits(integer = 5 , fraction = 0)
    @NotNull
    private Integer payment_platform_id; //接口编号
    @org.hibernate.validator.constraints.NotEmpty
    private String merchant_name;
    @org.hibernate.validator.constraints.NotEmpty
    private String merchant_no;
    private Integer order_page_id;
    private String merchant_descr;
    private Integer accumulate_record;
    private Integer accumulate_amount;
    private Integer max_stop_amount;
    private String merchant_status;
    private String signature_key;
    private Date create_date;
    private Date update_date;
    private String submit_url;
    private Integer sort_value;
    private Date last_trading_time;
    private String platform_no;
    private String signature_type;
    private String rsa_merchant_private_key;
    private String rsa_merchant_public_key;
    private String rsa_server_public_key;
    private String pfx_mer_public_key_path;
    private String pfx_mer_private_key_path;
    private String pfx_mer_private_key_pwd;
    private String pfx_ser_public_key_path;
    private String group_area;
    private String is_recharge_random_decimal;
    private String merchant_pwd;
    private Blob pfx_mer_public_key_file;
    private Blob pfx_mer_private_key_file;
    private Blob pfx_ser_public_key_file;
    private String qrcode_file_name;
    private Blob qrcode_file_content;
    private String is_pc_used;
    private String is_wap_used;
    private String is_10_multiple_reduce_1;
    private String is_only_integer;
    private Integer recharge_amount_min;
    private Integer recharge_amount_max;
    private Integer fee_percentage;
    private String is_online_payment_used;
    private String sp_limit;
    private String quick_amount_group;
    private String is_recharge_center_used;
    private String recharge_center_type;
    private String is_recharge_random_integer;
    private String recharge_random_integer_digit;
    private String recharge_random_decimal_digit;
    private Integer recharge_times_limit;
    private String qrcode_file;

    public String getQrcode_file() {
        return qrcode_file;
    }

    public void setQrcode_file(String qrcode_file) {
        this.qrcode_file = qrcode_file;
    }

    public Integer getOrder_page_id() {
        return order_page_id;
    }

    public void setOrder_page_id(Integer order_page_id) {
        this.order_page_id = order_page_id;
    }

    public String getMerchant_descr() {
        return merchant_descr;
    }

    public void setMerchant_descr(String merchant_descr) {
        this.merchant_descr = merchant_descr;
    }

    public Integer getAccumulate_record() {
        return accumulate_record;
    }

    public void setAccumulate_record(Integer accumulate_record) {
        this.accumulate_record = accumulate_record;
    }

    public Integer getAccumulate_amount() {
        return accumulate_amount;
    }

    public void setAccumulate_amount(Integer accumulate_amount) {
        this.accumulate_amount = accumulate_amount;
    }

    public Integer getMax_stop_amount() {
        return max_stop_amount;
    }

    public void setMax_stop_amount(Integer max_stop_amount) {
        this.max_stop_amount = max_stop_amount;
    }

    public String getMerchant_status() {
        return merchant_status;
    }

    public void setMerchant_status(String merchant_status) {
        this.merchant_status = merchant_status;
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

    public String getSubmit_url() {
        return submit_url;
    }

    public void setSubmit_url(String submit_url) {
        this.submit_url = submit_url;
    }

    public Integer getSort_value() {
        return sort_value;
    }

    public void setSort_value(Integer sort_value) {
        this.sort_value = sort_value;
    }

    public Date getLast_trading_time() {
        return last_trading_time;
    }

    public void setLast_trading_time(Date last_trading_time) {
        this.last_trading_time = last_trading_time;
    }

    public String getPfx_mer_public_key_path() {
        return pfx_mer_public_key_path;
    }

    public void setPfx_mer_public_key_path(String pfx_mer_public_key_path) {
        this.pfx_mer_public_key_path = pfx_mer_public_key_path;
    }

    public String getPfx_mer_private_key_path() {
        return pfx_mer_private_key_path;
    }

    public void setPfx_mer_private_key_path(String pfx_mer_private_key_path) {
        this.pfx_mer_private_key_path = pfx_mer_private_key_path;
    }

    public String getPfx_mer_private_key_pwd() {
        return pfx_mer_private_key_pwd;
    }

    public void setPfx_mer_private_key_pwd(String pfx_mer_private_key_pwd) {
        this.pfx_mer_private_key_pwd = pfx_mer_private_key_pwd;
    }

    public String getPfx_ser_public_key_path() {
        return pfx_ser_public_key_path;
    }

    public void setPfx_ser_public_key_path(String pfx_ser_public_key_path) {
        this.pfx_ser_public_key_path = pfx_ser_public_key_path;
    }

    public String getGroup_area() {
        return group_area;
    }

    public void setGroup_area(String group_area) {
        this.group_area = group_area;
    }

    public String getIs_recharge_random_decimal() {
        return is_recharge_random_decimal;
    }

    public void setIs_recharge_random_decimal(String is_recharge_random_decimal) {
        this.is_recharge_random_decimal = is_recharge_random_decimal;
    }

    public Blob getPfx_mer_public_key_file() {
        return pfx_mer_public_key_file;
    }

    public void setPfx_mer_public_key_file(Blob pfx_mer_public_key_file) {
        this.pfx_mer_public_key_file = pfx_mer_public_key_file;
    }

    public Blob getPfx_mer_private_key_file() {
        return pfx_mer_private_key_file;
    }

    public void setPfx_mer_private_key_file(Blob pfx_mer_private_key_file) {
        this.pfx_mer_private_key_file = pfx_mer_private_key_file;
    }

    public Blob getPfx_ser_public_key_file() {
        return pfx_ser_public_key_file;
    }

    public void setPfx_ser_public_key_file(Blob pfx_ser_public_key_file) {
        this.pfx_ser_public_key_file = pfx_ser_public_key_file;
    }

    public String getQrcode_file_name() {
        return qrcode_file_name;
    }

    public void setQrcode_file_name(String qrcode_file_name) {
        this.qrcode_file_name = qrcode_file_name;
    }

    public Blob getQrcode_file_content() {
        return qrcode_file_content;
    }

    public void setQrcode_file_content(Blob qrcode_file_content) {
        this.qrcode_file_content = qrcode_file_content;
    }

    public String getIs_pc_used() {
        return is_pc_used;
    }

    public void setIs_pc_used(String is_pc_used) {
        this.is_pc_used = is_pc_used;
    }

    public String getIs_wap_used() {
        return is_wap_used;
    }

    public void setIs_wap_used(String is_wap_used) {
        this.is_wap_used = is_wap_used;
    }

    public String getIs_10_multiple_reduce_1() {
        return is_10_multiple_reduce_1;
    }

    public void setIs_10_multiple_reduce_1(String is_10_multiple_reduce_1) {
        this.is_10_multiple_reduce_1 = is_10_multiple_reduce_1;
    }

    public String getIs_only_integer() {
        return is_only_integer;
    }

    public void setIs_only_integer(String is_only_integer) {
        this.is_only_integer = is_only_integer;
    }

    public Integer getRecharge_amount_min() {
        return recharge_amount_min;
    }

    public void setRecharge_amount_min(Integer recharge_amount_min) {
        this.recharge_amount_min = recharge_amount_min;
    }

    public Integer getRecharge_amount_max() {
        return recharge_amount_max;
    }

    public void setRecharge_amount_max(Integer recharge_amount_max) {
        this.recharge_amount_max = recharge_amount_max;
    }

    public Integer getFee_percentage() {
        return fee_percentage;
    }

    public void setFee_percentage(Integer fee_percentage) {
        this.fee_percentage = fee_percentage;
    }

    public String getIs_online_payment_used() {
        return is_online_payment_used;
    }

    public void setIs_online_payment_used(String is_online_payment_used) {
        this.is_online_payment_used = is_online_payment_used;
    }

    public String getSp_limit() {
        return sp_limit;
    }

    public void setSp_limit(String sp_limit) {
        this.sp_limit = sp_limit;
    }

    public String getQuick_amount_group() {
        return quick_amount_group;
    }

    public void setQuick_amount_group(String quick_amount_group) {
        this.quick_amount_group = quick_amount_group;
    }

    public String getIs_recharge_center_used() {
        return is_recharge_center_used;
    }

    public void setIs_recharge_center_used(String is_recharge_center_used) {
        this.is_recharge_center_used = is_recharge_center_used;
    }

    public String getRecharge_center_type() {
        return recharge_center_type;
    }

    public void setRecharge_center_type(String recharge_center_type) {
        this.recharge_center_type = recharge_center_type;
    }

    public String getIs_recharge_random_integer() {
        return is_recharge_random_integer;
    }

    public void setIs_recharge_random_integer(String is_recharge_random_integer) {
        this.is_recharge_random_integer = is_recharge_random_integer;
    }

    public String getRecharge_random_integer_digit() {
        return recharge_random_integer_digit;
    }

    public void setRecharge_random_integer_digit(String recharge_random_integer_digit) {
        this.recharge_random_integer_digit = recharge_random_integer_digit;
    }

    public String getRecharge_random_decimal_digit() {
        return recharge_random_decimal_digit;
    }

    public void setRecharge_random_decimal_digit(String recharge_random_decimal_digit) {
        this.recharge_random_decimal_digit = recharge_random_decimal_digit;
    }

    public Integer getRecharge_times_limit() {
        return recharge_times_limit;
    }

    public void setRecharge_times_limit(Integer recharge_times_limit) {
        this.recharge_times_limit = recharge_times_limit;
    }





    @Override
    public String toString() {
        return "MerchantVO{" +
                "merchantId='" + merchantId + '\'' +
                ", payment_platform_id='" + payment_platform_id + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", merchant_no='" + merchant_no + '\'' +
                ", platform_no='" + platform_no + '\'' +
                ", merchant_pwd='" + merchant_pwd + '\'' +
                ", signature_key='" + signature_key + '\'' +
                ", signature_type='" + signature_type + '\'' +
                ", rsa_merchant_private_key='" + rsa_merchant_private_key + '\'' +
                ", rsa_merchant_public_key='" + rsa_merchant_public_key + '\'' +
                ", rsa_server_public_key='" + rsa_server_public_key + '\'' +
                '}';
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPayment_platform_id() {
        return payment_platform_id;
    }
    public void setPayment_platform_id(Integer payment_platform_id) {
        this.payment_platform_id = payment_platform_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }
    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_no() {
        return merchant_no;
    }
    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getPlatform_no() {
        return platform_no;
    }

    public void setPlatform_no(String platform_no) {
        this.platform_no = platform_no;
    }

    public String getMerchant_pwd() {
        return merchant_pwd;
    }

    public void setMerchant_pwd(String merchant_pwd) {
        this.merchant_pwd = merchant_pwd;
    }

    public String getSignature_key() {
        return signature_key;
    }

    public void setSignature_key(String signature_key) {
        this.signature_key = signature_key;
    }

    public String getSignature_type() {
        return signature_type;
    }

    public void setSignature_type(String signature_type) {
        this.signature_type = signature_type;
    }

    public String getRsa_merchant_private_key() {
        return rsa_merchant_private_key;
    }

    public void setRsa_merchant_private_key(String rsa_merchant_private_key) {
        this.rsa_merchant_private_key = rsa_merchant_private_key;
    }

    public String getRsa_merchant_public_key() {
        return rsa_merchant_public_key;
    }

    public void setRsa_merchant_public_key(String rsa_merchant_public_key) {
        this.rsa_merchant_public_key = rsa_merchant_public_key;
    }

    public String getRsa_server_public_key() {
        return rsa_server_public_key;
    }

    public void setRsa_server_public_key(String rsa_server_public_key) {
        this.rsa_server_public_key = rsa_server_public_key;
    }


}
