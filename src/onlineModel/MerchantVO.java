package onlineModel;

import javax.validation.constraints.NotEmpty;

public class MerchantVO {
    private String merchantId;  //自动新增的商户流水号


    @NotEmpty
    private String payment_platform_id; //接口编号

    @NotEmpty
    private String merchant_name;

    @NotEmpty
    private String merchant_no;
    private String platform_no;
    private String merchant_pwd;
    private String signature_key;
    private String signature_type;
    private String rsa_merchant_private_key;
    private String rsa_merchant_public_key;
    private String rsa_server_public_key;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @NotEmpty
    public String getPayment_platform_id() {
        return payment_platform_id;
    }
    public void setPayment_platform_id(String payment_platform_id) {
        this.payment_platform_id = payment_platform_id;
    }

    @NotEmpty
    public String getMerchant_name() {
        return merchant_name;
    }
    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    @NotEmpty
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
