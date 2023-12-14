package py.com.enter.enterclient.dto.fcm;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SendMultipleFcmDto {
    private List<String> fcmTokens;

	private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
    
    
    
	
	
    
}
