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
		@Path("/myaccount/{custid}")
	    @Produces(MediaType.APPLICATION_JSON)
		public String getMyAccount(@PathParam("custid") String custid) {
			
			//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/myaccount/143
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select c from Customer c where c.customerId='" + custid + "'");
			Customer cust = (Customer)q.getSingleResult();
			
			Gson gson = new Gson();
			Type type = new TypeToken<Customer>(){}.getType();
			String json = gson.toJson(cust, type);
			
			em.close();
			factory.close();
			
			
	        return json;		        
		}
		
		@GET
		@Path("/updatecustomer/{custid}")
	    @Produces(MediaType.APPLICATION_JSON)
		public String getUpdateCustAccount(@PathParam("custid") int custid,@FormParam("cust") String custUpdate) {
			
			//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/updatecustomer/143
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select c from Customer c where c.customerId='" + custid + "'");
						
			Customer c1=em.find(Customer.class, custid);
			//*****************************************************
			//to convert JSON data to entity		
			String request = custUpdate;//JSON
			Gson gson = new Gson();				
			Type type = new TypeToken<Customer>(){}.getType();
			Package p = gson.fromJson(request, type);
			
			//won't work without transaction, throws exception
			  em.getTransaction().begin();
			  em.persist(p);
			  em.getTransaction().commit();			
			//****************************************************
			
			em.close();
			factory.close();			
			
	        return "hallelujah";//test string for success message        
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
	public String postPackage(@FormParam("pack") String pack) {
				
				//to convert JSON data to entity		
				String request = pack;//JSON
				Gson gson = new Gson();				
				Type type = new TypeToken<Package>(){}.getType();
				Package p = gson.fromJson(request, type);
				
				// pass the entity to persistence framework
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
				EntityManager em = factory.createEntityManager();
				
				//won't work without transaction, throws exception
				  em.getTransaction().begin();
				  em.persist(p);
				  em.getTransaction().commit();

				
				
		        return "allo";// test string just to get status 	
	}
	
	
	//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/createpackage
	@POST
	@Path("/updatepassword")
    @Produces(MediaType.TEXT_PLAIN)
	public String postUpdatePassword(@FormParam("userid") int userid, @FormParam("password") String password) {
				
		
		//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/updatepassword
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
		EntityManager em = factory.createEntityManager();
	
		Customer cust= em.find(Customer.class, userid);//retrieve data for a specific userid received via form data
		em.getTransaction().begin();
		cust.setCustPassword(password);//update password for a specific user
		em.getTransaction().commit();
		
		return "password updated";// test string just to get status 	
	}

	//http://localhost:8080/ProjectTravelExperts/rs/travelexperts/createbooking
	@POST
	@Path("/createbooking")
    @Produces(MediaType.TEXT_PLAIN)
	public String postBooking(@FormParam("book") String book) {
				
				//to convert JSON data to entity		
				String request = book;//JSON
	
				Gson gson = new Gson();				
				Type type = new TypeToken<Booking>(){}.getType();
				Booking b= gson.fromJson(request, type);
				//Package p = gson.fromJson(request, type);
				
				// pass the entity to persistence framework
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectTravelExperts");
				EntityManager em = factory.createEntityManager();
				
				//won't work without transaction, throws exception
				  em.getTransaction().begin();
				  em.persist(b);
				  em.getTransaction().commit();

				
				
		        return "Success";// test string just to get status 	
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
