package services;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
//import org.eclipse.persistence.internal.oxm.schema.model.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Booking;
import model.Customer;
import model.Package;

//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/test/lima
@Path("/travelexperts")
public class SimpleCustRestService {
		
		@GET
		@Path("/test/{utest}")
	    @Produces(MediaType.TEXT_PLAIN)
		public String getSomething(@PathParam("utest") String utest) {
			
			Gson gson = new Gson();
			        return utest;	
		}
		
		
		@GET
		@Path("/mybookings/{custid}")
	    @Produces(MediaType.APPLICATION_JSON)
		public String getMyBookings(@PathParam("custid") String custid) {
			
			//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/mybookings/143
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
			EntityManager em = factory.createEntityManager();			
			Query q = em.createQuery("select b from Booking b where b.customerId='" + custid + "'");
			//Customer cust = (Customer)q.getSingleResult();//There could be multiple bookings for a single customer
			
			List<Booking[]> book=q.getResultList();//since there could be multiple bookings we ll need a list
			//Booking book=(Booking)q.getResultList();
			
			Gson gson = new Gson();
			//Type type = new TypeToken<Booking[]>(){}.getType(); //don't need it coz the list can be directly translated 
			//to JSON object
			String json = gson.toJson(book);
			
			em.close();
			factory.close();			
			//return Response.ok(book).build(); //doesn't work
			return json;	
			        
		}
		
		@GET
		@Path("/getpackages")
	    @Produces(MediaType.APPLICATION_JSON)
		public String getPackages() {
			
			//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/getpackages
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
			EntityManager em = factory.createEntityManager();			
			Query q = em.createQuery("select p from Package p ");
			//Customer cust = (Customer)q.getSingleResult();//There could be multiple bookings for a single customer
			
			List<Package> pack=q.getResultList();//since there could be multiple bookings we ll need a list
			//Booking book=(Booking)q.getResultList();
			
			Gson gson = new Gson();
			//Type type = new TypeToken<Booking[]>(){}.getType(); //don't need it coz the list can be directly translated 
			//to JSON object
			String json = gson.toJson(pack);
			
			em.close();
			factory.close();			
			//return Response.ok(book).build(); //doesn't work
			return json;	
			        
		}

		//method to get details about a single package
		@GET
		@Path("/getpackagedetails/{packid}")
	    @Produces(MediaType.APPLICATION_JSON)
		public String getPackage(@PathParam("packid") String packid) {
			
			//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/getpackagedetails
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select p from Package p  where p.packageId='" + packid + "'");
			//Customer cust = (Customer)q.getSingleResult();
			Package pack=(Package)q.getSingleResult();
			Gson gson = new Gson();
			Type type = new TypeToken<Package>(){}.getType();
			String json = gson.toJson(pack, type);
			
			em.close();
			factory.close();
			
			
	        return json;        
		}


		//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/createpackage
	@POST
	@Path("/createpackage")
    @Produces(MediaType.TEXT_PLAIN)
	public String postPackage(@FormParam("userid") String userid,@FormParam("name") String name) {
				
				StringBuilder sb = new StringBuilder(userid);		
				String request = userid;//JSON
				Gson gson = new Gson();				
				Type type = new TypeToken<Package>(){}.getType();
				Package p = gson.fromJson(request, type);
				
		        return p.getPkgName();	
	}

	@PUT
	@Path("/<add method name here>")
    @Produces(MediaType.TEXT_PLAIN)
	public String putSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		     
		return request;	
	}

	@DELETE
	@Path("/<add method name here>")
	public void deleteSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		
			}
}
