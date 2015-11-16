package edu.sjsu.cmpe275.lab1;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

	ApplicationContext cntxt;
	SecretService service;
	public Application()
	{
		cntxt = new ClassPathXmlApplicationContext("spring.xml");
		service = (SecretService)cntxt.getBean("SecretService");
	}
	public User fetchUserIdFromBean(String userId)
	{
		User usr = (User)cntxt.getBean(userId);
		return usr;
	}
	public UUID storeSecret(String userId, Secret secret)
	{
		return service.storeSecret(userId, secret);
	}
	public Secret readSecret(String userId, UUID secretId)
	{
		return service.readSecret(userId, secretId);
	}
	public void shareSecret(String userId, UUID secretId, String targetUserId)
	{
		service.shareSecret(userId, secretId, targetUserId);
	}
	public void unshareSecret(String userId, UUID secretId, String targetUserId)
	{
		service.unshareSecret(userId, secretId, targetUserId);
	}
		
	}
