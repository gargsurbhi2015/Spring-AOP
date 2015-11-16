package edu.sjsu.cmpe275.lab1;

import java.util.UUID;

public class Secret {
	
	public Secret(){}
	private UUID secretId;
	
	public UUID getSecretId() {

		return secretId;
	}
	public void setSecretId(UUID secretId) {
		
		this.secretId = secretId;
	}
	public UUID generateSecretId()
	{
		return this.secretId = UUID.randomUUID();
	}
	
}
