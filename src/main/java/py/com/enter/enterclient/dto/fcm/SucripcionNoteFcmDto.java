package py.com.enter.enterclient.dto.fcm;

import java.util.List;
import java.util.Map;

public class SucripcionNoteFcmDto {

	private String subject;
    private String content;
    private Map<String, String> data;
	List<String> tokens;
	
	
	public SucripcionNoteFcmDto() {
		super();
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
	public List<String> getTokens() {
		return tokens;
	}
	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	
	
	
}
