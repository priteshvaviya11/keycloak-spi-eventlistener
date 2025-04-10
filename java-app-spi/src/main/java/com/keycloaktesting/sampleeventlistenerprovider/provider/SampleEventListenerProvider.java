package com.keycloaktesting.sampleeventlistenerprovider.provider;

import java.util.Map;
import java.util.Map.Entry;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class SampleEventListenerProvider implements EventListenerProvider {
	
	private final String serverUri;
	
	/*public SampleEventListenerProvider() {
		
	}*/

	public SampleEventListenerProvider(String serverUri) {
		this.serverUri = serverUri;
	}

	@Override
    public void onEvent(Event event) {
		if(event.getType().toString() == "LOGIN") {
			System.out.println("Displaying Server URI as passed from the Factory varaible setup : " + this.serverUri);
			System.out.println("Event Occurred: XXXXXXXXXXXXXXXXXXXXXXX" + toString(event));
		}
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        System.out.println("Admin Event Occurred:" + toString(adminEvent));
    }

    @Override
    public void close() {

    }
	
	private String toString(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\": \"");
        sb.append(event.getType());
        sb.append("\", \"realmId\": \"");
        sb.append(event.getRealmId());
        sb.append("\", \"clientId\": \"");
        sb.append(event.getClientId());
        sb.append("\", \"userId\": \"");
        sb.append(event.getUserId());
        sb.append("\", \"ipAddress\": \"");
        sb.append(event.getIpAddress());
        sb.append("\"");

        if (event.getError() != null) {
            sb.append(", \"error\": \"");
            sb.append(event.getError());
            sb.append("\"");
        }
        sb.append(", \"details\": {");
        if (event.getDetails() != null) {
            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
                sb.append("\"");
                sb.append(e.getKey());
                sb.append("\": \"");
                sb.append(e.getValue());
                sb.append("\",");

            }
            if (Character.compare(sb.charAt(sb.length()-1), ',') == 0) {
				//Removes Extra Comma to make JSON valid
                sb.deleteCharAt(sb.length()-1);
            }
        }

        sb.append("}}");

        return sb.toString();
    }

	
	private String toString(AdminEvent adminEvent) {
	       StringBuilder sb = new StringBuilder();

	        sb.append("{\"type\": \"");
	        sb.append(adminEvent.getOperationType());
	        sb.append("\", \"realmId\": \"");
	        sb.append(adminEvent.getAuthDetails().getRealmId());
	        sb.append("\", \"clientId\": \"");
	        sb.append(adminEvent.getAuthDetails().getClientId());
	        sb.append("\", \"userId\": \"");
	        sb.append(adminEvent.getAuthDetails().getUserId());
	        sb.append("\", \"ipAddress\": \"");
	        sb.append(adminEvent.getAuthDetails().getIpAddress());
	        sb.append("\", \"resourcePath\": \"");
	        sb.append(adminEvent.getResourcePath());
	        sb.append("\"");

	        if (adminEvent.getError() != null) {
	            sb.append(", \"error\": \"");
	            sb.append(adminEvent.getError());
	            sb.append("\"");
	        }
	        sb.append("}");
	        return sb.toString();
    }

}
