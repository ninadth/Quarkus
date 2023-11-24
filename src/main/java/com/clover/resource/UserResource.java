package com.clover.resource;


import com.clover.entity.User;
import com.clover.utility.UserNameEncryptAndDecrypt;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;



@Path("/user-resource")
public class UserResource {
	
	@Inject
    EntityManager entityManager;
	
//	@Inject
//	private UserRepository userRepository;

    //create-user
    @POST
    @Path("/createUser")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws Exception{
    	user.setUserName(UserNameEncryptAndDecrypt.encryption(user.getUserName()));
    	User.persist(user);
    	if (user.isPersistent()) {
			return Response.created(URI.create("/laptop/" + user.getid())).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
    }
	
    //get single user
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String userId) {
    	  User user = User.findById(userId);
          return Response.ok(user).build();
      }
    

    //Get-All-user
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {

    	  List<User> users = User.listAll();
          return Response.ok(users).build();
    }
    
    //getUsersInPage
    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsersInPage(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("2") int pageSize) {
    
    	   PanacheQuery<User> query = User.findAll();
    	   List<User> list = query.page(page,pageSize).list();
    	   System.out.println("NO OF RECORDS:- "+list);
          return list;
    }              
    
    
    //COUNT OF USER IN DATABASE
    @GET
    @Path("/userCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCount() {
    	long count = User.count();
        if(count !=0) {
        System.out.println(" NUMBER OF USER IN DATBASE: "+count);
        }else {
            System.out.println("NO USER IN YOUR DATABASE");
        }
        return Response.ok(" NUMBER OF USER IN DATBASE: "+count).build();
        }
   
    //getAllListOfUser BY ADDRESS
    @GET
    @Path("/users/{userAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllListOfUser(@PathParam("userAddress") String userAddress, String userAdd) {
    	List<User> listUser = User.list(userAddress,userAdd);
    	return Response.ok(listUser).build();
    }
    
    //getUsersInRange IN STARTING DATE TO ENDING DATE
    @GET
    @Path("/range")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersInRange(
            @QueryParam("startDate") String startDateString,
            @QueryParam("endDate") String endDateString) {
    	try {
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
      
        List<User> userList = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.date BETWEEN :startDate AND :endDate",User.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
               System.out.println(startDate+" & "+endDate+" between all record:- "+userList);
        return Response.ok(userList).build();
    	} catch (Exception e) {
            // Handle exceptions appropriately
    		e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid request parameters.")
                    .build();
        }
    }
    	//count record in range of date
    	@GET	
    	@Path("/getCountOfRangeOfDate")
    	@Produces(MediaType.APPLICATION_JSON)
    	public Response getCountOfRangeOfDate(
    			@QueryParam("startDate") String startDateString,
                @QueryParam("endDate") String endDateString) {
    		
    		try {
    	        LocalDate startDate = LocalDate.parse(startDateString);
    	        LocalDate endDate = LocalDate.parse(endDateString);
    	      
    	        List<User> userList = entityManager.createQuery(
    	                "SELECT u FROM User u WHERE u.date BETWEEN :startDate AND :endDate",User.class)
    	                .setParameter("startDate", startDate)
    	                .setParameter("endDate", endDate)
    	                .getResultList();
    	        int size = userList.size();
    	        System.out.println("NO OF RECORD IN THIS RANGE OF DATE:- "+size);
    	        return Response.ok(size).build();
    	        
    	    	} catch (Exception e) {
    	            // Handle exceptions appropriately
    	    		e.printStackTrace();
    	            return Response.status(Response.Status.BAD_REQUEST)
    	                    .entity("Invalid request parameters.")
    	                    .build();
    	
    	       }
}
    }  
