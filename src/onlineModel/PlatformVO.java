package onlineModel;


import org.springframework.stereotype.Component;

@Component
public class PlatformVO { //接口資料的bean
	private String platform_name;

    private String platform_url;

    private String platform_id;
    private String merchant_no_tips;
    private String merchant_pwd_name;
    private String merchant_pwd_tips;
    private String platform_no_name;
    private String platform_no_tips;
    private String rsa_merchant_private_key_tips;
    private String rsa_server_public_key_tips;

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

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }
}
