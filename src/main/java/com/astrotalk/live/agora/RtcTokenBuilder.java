package com.astrotalk.live.agora;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RtcTokenBuilder {
    static final String appId = "799b1cb0daaf4fa0b7f7bbb96e809d80";
    static final String appCertificate = "3c70820835eb47bc99b48edb7860d62e";
   // static final String account =  "";
	public enum Role {

	    Role_Publisher(1),
        Role_Subscriber(2),
        Role_Admin(101);

        public int initValue;
        Role(int initValue) {
            this.initValue = initValue;
        }
    }


    

    public String buildTokenWithUserAccount(String channelName, Role role,long userId) {
    	int privilegeTs = (int)(System.currentTimeMillis()/1000) + 600;
    	// Assign appropriate access privileges to each role.
    	AccessToken builder = new AccessToken(appId, appCertificate, channelName, String.valueOf(userId));
    	builder.addPrivilege(AccessToken.Privileges.kJoinChannel, privilegeTs);
    	if (role == Role.Role_Publisher || role == Role.Role_Subscriber || role == Role.Role_Admin) {
    		builder.addPrivilege(AccessToken.Privileges.kPublishAudioStream, privilegeTs);
    		builder.addPrivilege(AccessToken.Privileges.kPublishVideoStream, privilegeTs);
    		builder.addPrivilege(AccessToken.Privileges.kPublishDataStream, privilegeTs);
    	}
    	
    	try {
			return builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
}
