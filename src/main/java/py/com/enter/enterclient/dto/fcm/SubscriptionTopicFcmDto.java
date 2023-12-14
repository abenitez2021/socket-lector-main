package py.com.enter.enterclient.dto.fcm;

import java.util.List;

import lombok.Data;

@Data
public class SubscriptionTopicFcmDto {
	    
	    private String topic;
	    private List<String> tokens;
	   
}
