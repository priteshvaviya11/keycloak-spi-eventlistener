package com.keycloaktesting.sampleeventlistenerprovider.provider;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SampleEventListenerProvider implements EventListenerProvider {
	
	private final OkHttpClient httpClient = new OkHttpClient();
    private final MediaType JSON = MediaType.parse("application/json, charset=utf-8");
	
	private final String serverUri;
	
	/*public SampleEventListenerProvider() {
		
	}*/

	public SampleEventListenerProvider(String serverUri) {
		this.serverUri = serverUri;
	}

	@SuppressWarnings("deprecation")
	@Override
    public void onEvent(Event event) {
		/*if(event.getType().toString() == "LOGIN") {
			System.out.println("Displaying Server URI as passed from the Factory varaible setup : " + this.serverUri);
			System.out.println("Event Occurred: XXXXXXXXXXXXXXXXXXXXXXX" + toString(event));
		}*/
		
		//Let's get the events and publish them on to our rest api webhook endpoint.
		String stringEvent = toString(event);
		
		try {
            RequestBody body = RequestBody.create(JSON, stringEvent);

            Request request = new Request.Builder()
                    .url(this.serverUri)
                    .post(body)
                    .addHeader("User-Agent", "KeycloakHttp Bot")
                    .addHeader("Content-Type", "application/json")
                    .build();

            
            Response response = httpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
            }

            // Get response body
            System.out.println(response.body().string());
        } catch(Exception e) {
            System.out.println("UH OH!! " + e.toString());
            e.printStackTrace();
            return;
        }
		
		
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        //System.out.println("Admin Event Occurred:" + toString(adminEvent));
        
        String stringEvent = toString(adminEvent);
        try {
            RequestBody body = RequestBody.create(JSON, stringEvent);

            Request request = new Request.Builder()
                    .url(this.serverUri)
                    .post(body)
                    .addHeader("User-Agent", "KeycloakHttp Bot")
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = httpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
            }

            // Get response body
            System.out.println(response.body().string());
        } catch(Exception e) {
            System.out.println("UH OH!! " + e.toString());
            e.printStackTrace();
            return;
        }
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
