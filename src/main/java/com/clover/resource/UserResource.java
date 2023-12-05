package com.clover.resource;


import com.clover.entity.User;
import com.clover.exception.ResourceNotFoundException;
import com.clover.utility.UserNameEncryptAndDecrypt;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.jboss.logging.Logger;




@Path("/user-resource")
public class UserResource {
	
	@Inject
    EntityManager entityManager;
	
	private static final Logger log=Logger.getLogger(UserResource.class);
	
//	@Inject
//	private UserRepository userRepository;

	/**
	 * @author Ninad Thakre
	 *  @apiNote THIS API FOR CREATE USER
	 * @param user
	 * @return
	 * @throws Exception
	 */
    //create-user
    @POST
    @Path("/createUser")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid User user) throws Exception{
    	
    	log.info("starting a request for creating user");
    	user.setUserName(UserNameEncryptAndDecrypt.encryption(user.getUserName()));
    	User.persist(user);
    	log.info("ending a request for creating user");
    	if (user.isPersistent()) {
    		
    		return Response.created(URI.create("/user/" + user.getid())).build();
			
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
    	
    }
	
    /**
     * @apiNote THIS API FOR GET SINGLE USER BY USER ID
     * @param userId
     * @return
     */
    //get single user
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long userId) {
    	  User user = User.findById(userId);
    	  if(user==null) {
    		  throw new ResourceNotFoundException("RESOURCE NOT FOUND WITH ID:"+userId);
    	  }
          return Response.ok(user).build();
      }
    

    /**
     * @apiNote THIS API FOR GET ALL USERS
     * @return
     */
    //Get-All-user
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {

    	  List<User> users = User.listAll();
          return Response.ok(users).build();
    }
    
    /**
     * @apiNote THIS API FOR GET USER ON THE BASIS OF PAGINATION
     * @param page
     * @param pageSize
     * @return
     */
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
    
    /**
     * @apiNote THIS API FOR COUNT OF USER IN DATABASE
     * @return
     */
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
   
    /**
     * @apiNote THIS API FOR GET LIST OF USER BY USER ADDRESS
     * @param userAddress
     * @param userAdd
     * @return
     */
    //getAllListOfUser BY ADDRESS
    @GET
    @Path("/users/{userAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllListOfUser(@PathParam("userAddress") String userAddress, String userAdd) {
    	List<User> listUser = User.list(userAddress,userAdd);
    	return Response.ok(listUser).build();
    }
    
    /**
     * @apiNote THIS API FOR GET USER IN RANGE BETWEEN STARTING DATE AND ENDING DATE
     * @param startDateString
     * @param endDateString
     * @return
     */
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
        /**
         * @apiNote THIS API FOR COUNT RECORD IN RANGE OF DATE
         * @param startDateString
         * @param endDateString
         * @return
         */
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
    	
    	/**
    	 * @apiNote THIS API FOR DELETE USER BY USER ID
    	 * @param userId
    	 * @return
    	 */
    	@DELETE
    	@Transactional
    	@Path("user/deleteUser/{userId}")
    	public Response deleteUserById(@PathParam("userId") Long userId) {
    		
    		User user = User.findById(userId);
    		
    		if(user==null) {
        		  throw new ResourceNotFoundException("RESOURCE NOT FOUND WITH ID:"+userId);
        	  }
    		
    		user.delete();
			return Response.ok(true).build();
    
    	}
    	}

