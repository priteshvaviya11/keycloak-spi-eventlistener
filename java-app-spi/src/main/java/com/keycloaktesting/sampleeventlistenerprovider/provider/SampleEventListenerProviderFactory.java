package com.keycloaktesting.sampleeventlistenerprovider.provider;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SampleEventListenerProviderFactory implements EventListenerProviderFactory {

	private String serverUri;
	
	@Override
	public EventListenerProvider create(KeycloakSession session) {
		return new SampleEventListenerProvider(serverUri);
	}

	@Override
	public void init(Scope config) {
		this.serverUri = config.get("server-uri");
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {

	}

	@Override
	public void close() {
		
	}

	@Override
	public String getId() {
		return "http";
	}

}
