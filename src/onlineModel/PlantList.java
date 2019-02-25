package onlineModel;


import org.springframework.stereotype.Component;

@Component
public class PlantList {
	private String Name;
	private String Url;
	private int id;

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
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
