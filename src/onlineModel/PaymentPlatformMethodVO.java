package onlineModel;

import java.io.Serializable;
import java.sql.Date;

public class PaymentPlatformMethodVO implements Serializable {

    private Integer payment_platform_id;
    private String payment_method_id;
    private Date create_date;
    private Date update_date;

    public Integer getPayment_platform_id() {
        return payment_platform_id;
    }

    public void setPayment_platform_id(Integer payment_platform_id) {
        this.payment_platform_id = payment_platform_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
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
