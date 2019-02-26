package onlineModel;


import org.springframework.stereotype.Component;

@Component
public class PlatformVO { //接口資料的bean
	private String platform_name;
	private String platform_url;
    private String platform_id;


    @Override
    public String toString() {
        return "PlatformVO{" +
                "platform_name='" + platform_name + '\'' +
                ", platform_url='" + platform_url + '\'' +
                ", platform_id='" + platform_id + '\'' +
                '}';
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
