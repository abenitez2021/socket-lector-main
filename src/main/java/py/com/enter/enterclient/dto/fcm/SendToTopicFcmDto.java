package py.com.enter.enterclient.dto.fcm;

import java.util.Map;

import lombok.Data;

@Data
public class SendToTopicFcmDto {

    private String topic;
    
	private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
    
    
}
