package edu.sjsu.cmpe275.lab1;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecretServiceImpl implements SecretService {
	
	private static Map<String,UUID> userMap = new HashMap<String,UUID>();
	private static Map<UUID, Set<String>> secretMap = new HashMap<UUID,Set<String>>();
	
	public static Map<UUID, Set<String>> getSecretMap() {
		return secretMap;
	}
	public static void setSecretMap(Map<UUID, Set<String>> secretMap) {
		SecretServiceImpl.secretMap = secretMap;
	}
	
	public static Map<String, UUID> getUserMap() {
		return userMap;
	}
	public static void setUserMap(Map<String, UUID> userMap) {
		SecretServiceImpl.userMap = userMap;
	}
	public UUID storeSecret(String userId, Secret secret)
	{
		return null;		
	}
	public Secret readSecret(String userId, UUID secretId)
	{
		System.out.println(userId+" reads the secret of ID "+ secretId);
		return null;
	}
	public void shareSecret(String userId, UUID secretId, String targetUserId)
	{
		System.out.println(userId+" shares the secret of ID "+ secretId+" with "+targetUserId);
	}
	public void unshareSecret(String userId, UUID secretId, String targetUserId)
	{
		System.out.println(userId+" unshares the secret of ID "+ secretId+" with "+targetUserId);
	}
	
}
