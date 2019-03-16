package onlineModel;

import java.sql.Date;

public class MerchantPaymentVO {

    private Integer merchant_payment_id;
    private String type;
    private Integer merchant_id;
    private Integer payment_method_id;
    private Date create_date;
    private Date update_date;


    public Integer getMerchant_payment_id() {
        return merchant_payment_id;
    }

    public void setMerchant_payment_id(Integer merchant_payment_id) {
        this.merchant_payment_id = merchant_payment_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(Integer merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Integer getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(Integer payment_method_id) {
        this.payment_method_id = payment_method_id;
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

}
