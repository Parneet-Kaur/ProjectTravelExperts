package services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
 

public class SimpleCustRestServiceApplication  extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	 
	public SimpleCustRestServiceApplication() {
		singletons.add(new SimpleCustRestService());
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return null;
	}

}
