package edu.sjsu.cmpe275.lab1;
import java.util.UUID;

public interface SecretService {
	
	public UUID storeSecret(String userId, Secret secret);
	public Secret readSecret(String userId, UUID secretId);
	public void shareSecret(String userId, UUID secretId, String targetUserId);
	public void unshareSecret(String userId, UUID secretId, String targetUserId);

}
