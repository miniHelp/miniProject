package onlineModel;


import org.springframework.stereotype.Component;

@Component
public class PlatformVO { //接口資料的bean
	private String Name;
	private String Url;
	private String id;

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PlantList{" +
				"Name='" + Name + '\'' +
				", Url='" + Url + '\'' +
				", id=" + id +
				'}';
	}
}
