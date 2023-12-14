package py.com.enter.enterclient.dto.fcm;

import java.util.Map;

import lombok.Data;

@Data
public class SendOneFcmDto {
    private String fcmToken;

	private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
    
    
    
	public SendOneFcmDto() {
		super();
	}



	public SendOneFcmDto(String subject, String content, Map<String, String> data, String image) {
		super();
		this.subject = subject;
		this.content = content;
		this.data = data;
		this.image = image;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Map<String, String> getData() {
		return data;
	}



	public void setData(Map<String, String> data) {
		this.data = data;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getFcmToken() {
		return fcmToken;
	}



	public void setFcmToken(String token) {
		this.fcmToken = token;
	}
    
	
	
    
}
