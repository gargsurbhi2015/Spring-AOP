package src.test.java;

import org.junit.Test;

import edu.sjsu.cmpe275.lab1.Application;
import edu.sjsu.cmpe275.lab1.Secret;
import edu.sjsu.cmpe275.lab1.SecretService;
import edu.sjsu.cmpe275.lab1.SecretServiceImpl;
import edu.sjsu.cmpe275.lab1.UnauthorizedException;
import edu.sjsu.cmpe275.lab1.User;


public class Validations {

	SecretService service = new SecretServiceImpl();
	Application app = new Application();
	@Test (expected=UnauthorizedException.class)
	public void testA()
	{
	  Secret secret1 = new Secret();
	  User user1 = new User();
	  User user2 = new User();
	  user1=app.fetchUserIdFromBean("Alice");
	  user2=app.fetchUserIdFromBean("Bob");
	  System.out.println("Test Case A");
	  System.out.println("Bob trying to access Alice's Secret without it being shared. We get an Exception."); 
	  System.out.println("-------------------------------------------------------------------------------");
	  app.storeSecret(user1.getUserId(), secret1);
	  app.readSecret(user2.getUserId(), secret1.getSecretId());
	  System.out.println("Test Completed with no exception!");
	  System.out.println("-------------------------------------------------------------------------------");
	}
	@Test()
	public void testB() {
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		System.out.println("Test Case B");
		System.out.println("Bob trying to access Alice's Secret after it being shared. Bob can read this secret.");
		System.out.println("--------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.readSecret(user2.getUserId(), secret1.getSecretId());
		System.out.println("Test Completed with no exception!");
		System.out.println("--------------------------------------------------------------------------------");
	}
	@Test()
	public void testC() {
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case C");
		System.out.println("Alice shares her secret with Bob. Bob shares Alice's secret with Carl. Carl can read it");
		System.out.println("-----------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.shareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.readSecret(user3.getUserId(), secret1.getSecretId());
		System.out.println("Test Completed with no exception!");
		System.out.println("-----------------------------------------------------------------------------------");
	}
	@Test (expected=UnauthorizedException.class) 
	public void testD() {
		Secret secret1 = new Secret();
		Secret secret2 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case D");
		System.out.println("Alice shares her secret with Bob. Bob shares Carl's secret with Alice. We get an Exception!");
		System.out.println("---------------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.storeSecret(user3.getUserId(), secret2);
		app.shareSecret(user2.getUserId(), secret2.getSecretId(), user1.getUserId());
		System.out.println("Test Completed with no exception!");
		System.out.println("---------------------------------------------------------------------------------------");
	}
	@Test (expected=UnauthorizedException.class) 
	public void testE() {
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case E");
		System.out.println("Alice shares her secret with Bob. Bob shares Alice's secret with Carl. Alice unshares her secret with Carl. Carl can't read this secret!");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.shareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.unshareSecret(user1.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.readSecret(user3.getUserId(), secret1.getSecretId());
		System.out.println("Test Completed with no exception!");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
	}
	@Test (expected=UnauthorizedException.class) 
	public void testF() {
		
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case F");
		System.out.println("Alice shares her secret with Bob. Alice shared her secret with Carl. Carl shares Alice's secret with Bob. Alice unshares her secret with Bob. Bob can't read this secret!");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.shareSecret(user3.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.unshareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.readSecret(user2.getUserId(), secret1.getSecretId());
		System.out.println("Test Completed with no exception!");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test (expected=UnauthorizedException.class) 
	public void testG() {
		
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case G");
		System.out.println("Alice shares her secret with Bob. Bob shares Alice's secret with Carl. Alice unshares her secret with Bob. Bob shares Alice's secret with Carl again. We get an exception. But Carl can still read this secret.");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.shareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.unshareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.readSecret(user3.getUserId(), secret1.getSecretId());
		System.out.println("Test Completed with no exception!");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test (expected=UnauthorizedException.class) 
	public void testH() {
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case H");
		System.out.println("Alice shares secret with Bob. Carl tries to unshare with Bob. We get an exception");
		System.out.println("----------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.unshareSecret(user3.getUserId(), secret1.getSecretId(), user2.getUserId());
		System.out.println("Test Completed with no exception!");
		System.out.println("----------------------------------------------------------------------------------");
	}
	@Test (expected=UnauthorizedException.class) 
	public void testI() {
		Secret secret1 = new Secret();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		user2=app.fetchUserIdFromBean("Bob");
		user3=app.fetchUserIdFromBean("Carl");
		System.out.println("Test Case I");
		System.out.println("Alice shares secret with Bob.Bob shares it with Carl. Alice unshares it with Bob.Bob shares it with Carl with again. We get an exception");
		System.out.println("----------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.shareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.unshareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		app.unshareSecret(user1.getUserId(), secret1.getSecretId(), user2.getUserId());
		app.shareSecret(user2.getUserId(), secret1.getSecretId(), user3.getUserId());
		System.out.println("Test Completed with no exception!");
		System.out.println("----------------------------------------------------------------------------------");
	}
	@Test () 
	public void testJ() {
		Secret secret1 = new Secret();
		User user1 = new User();
		user1=app.fetchUserIdFromBean("Alice");
		System.out.println("Test Case H");
		System.out.println("Alice shares secret with Bob.Bob shares it with Carl. Alice unshares it with Bob.Bob shares it with Carl with again. We get an exception");
		System.out.println("----------------------------------------------------------------------------------");
		app.storeSecret(user1.getUserId(), secret1);
		app.storeSecret(user1.getUserId(), secret1);
		System.out.println("Test Completed with no exception!");
		System.out.println("----------------------------------------------------------------------------------");
	}
}
