package edu.sjsu.cmpe275.lab1;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

	@Before("execution(* edu.sjsu.cmpe275.lab1.SecretService.*(..))")
	public void doBefore(JoinPoint joinPoint) 
	{
	    String methodCalled = joinPoint.getSignature().getName();
		
		System.out.println("<<<<<AspectJ>>>>> DoBefore() is running. It intercepted : "+ methodCalled);
		
		if(methodCalled.equals("readSecret"))
		{   
			readSecretOperation(joinPoint);
		}
		else if(methodCalled.equals("shareSecret"))
		{   
			shareSecretOperation(joinPoint);
		}
		else if(methodCalled.equals("unshareSecret"))
		{
			unshareSecretOperation(joinPoint);
		}
	       
	}
	public void readSecretOperation(JoinPoint joinPoint)
	{     
	  Object[] parameters=(Object[])joinPoint.getArgs();
	  System.out.println(" parameters "+parameters.toString());
	  String userId = parameters[0].toString();
	  UUID secretId = (UUID) parameters[1];
	  System.out.println("<< " +userId + " is trying to access " + secretId + " >>");
	  Map<UUID,Set<String>> sharedSecrets = SecretServiceImpl.getSecretMap();
  
	  if(sharedSecrets.containsKey(secretId))
	  {
		  Set<String> tempSet = sharedSecrets.get(secretId);
		  if(tempSet.contains(userId))
		  {
			  System.out.println("Valid User :" + userId + " has access to read " + secretId);
		  }
		  else
		  {
			  System.out.println("Throw Exception : Not Authorized for " + userId + " to read " + secretId);
		    	throw new UnauthorizedException("Throw Exception : Not Authorized!!");
		  }
	  }
	  else
	  {
		  System.out.println("Throw Exception :" + secretId + " Not Shared by " + userId);
	      throw new UnauthorizedException("Throw Exception : Secret Not Shared!!");
	  }  
	}
	
	public void shareSecretOperation(JoinPoint joinPoint)
	{
		Object[] parameters=(Object[])joinPoint.getArgs();
		String userId=parameters[0].toString();
		UUID secretId = (UUID) parameters[1];
		String targetUserId=parameters[2].toString();
		Map<UUID,Set<String>> sharedSecrets = SecretServiceImpl.getSecretMap();
		
		if(!userId.equals(targetUserId) && (sharedSecrets.get(secretId).contains(userId)))
		{	
			if(sharedSecrets.containsKey(secretId))
			{
				sharedSecrets.get(secretId).add(targetUserId);
			}
			else
			{
				Set<String> results=new HashSet<String>();
				results.add(targetUserId);
				sharedSecrets.put(secretId, results);
			}
		}
		else
		{
			if(!(sharedSecrets.get(secretId).contains(userId)))
			{
				System.out.println("Throw Exception : Not authorised for " + userId + " to share " + secretId + " with " + targetUserId);
				throw new UnauthorizedException("Throw Exception : Not authorised for " + userId + " to share " + secretId + " with " + targetUserId);
			}
			else
			{
				System.out.println(" No Action.");
			}
		}
	}
	
	public void unshareSecretOperation(JoinPoint joinPoint)
	{
		Object[] parameters=(Object[])joinPoint.getArgs();
		String userId=parameters[0].toString();
		UUID secretId = (UUID) parameters[1];
		String targetUserId=parameters[2].toString();
		Map<String,UUID> userMap = SecretServiceImpl.getUserMap();
		Map<UUID,Set<String>> sharedSecrets = SecretServiceImpl.getSecretMap();
		
		if(!userId.equals(targetUserId))
		{
			if(userMap.containsKey(userId) && (userMap.get(userId).equals(secretId)) )
			{	
				if(sharedSecrets.containsKey(secretId))
				{   
					if(sharedSecrets.get(secretId).contains(targetUserId))
				    {
					  sharedSecrets.get(secretId).remove(targetUserId);
					  SecretServiceImpl.setSecretMap(sharedSecrets);
				    }
				    else
				    {
				    	System.out.println("Throw Exception - Target user:" + targetUserId + " does not exist in shared list.");
				    	throw new UnauthorizedException("Target user does not exist in shared list.");
				    }
			    }
				else
			    {
			    	System.out.println("Throw Exception : Secret - " + secretId + " Not Shared with " + targetUserId);
			    	throw new UnauthorizedException("Throw Exception : Secret Not Shared!!");
			    }
			}
			else
			{
				System.out.println("Throw Exception :" + userId + " hasn't shared " + secretId);
				throw new UnauthorizedException("User hasn't shared anything yet!!");
			}
		}
		else
		{
			System.out.println("Throw Exception :" +userId+" does not own "+ secretId);
			throw new UnauthorizedException(userId+" does not own "+ secretId);
		}
		
	}
	@After("execution(* edu.sjsu.cmpe275.lab1.SecretService.storeSecret(..))")
	public void logAfterStoreSecret(JoinPoint joinPoint)
	{
		 
		 Object[] parameters=(Object[])joinPoint.getArgs();
		 String userId = parameters[0].toString();
		 Secret secret = (Secret)parameters[1];
		 secret.setSecretId(secret.generateSecretId());
		 Map<String,UUID> userMap = SecretServiceImpl.getUserMap();
		 userMap.put(userId, secret.getSecretId());
		 Map<UUID,Set<String>> sharedSecrets = SecretServiceImpl.getSecretMap();
		 Set<String> tempUserSet = new HashSet<String>();
		 tempUserSet.add(userId);
		 sharedSecrets.put(secret.getSecretId(), tempUserSet);
		 System.out.println(userId+" creates a secret with ID "+secret.getSecretId());
		 
	}
	
}
