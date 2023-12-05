package com.clover;


import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.clover.entity.User;
import com.clover.resource.UserResource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;




@QuarkusTest
public class UserResourceTest {
	
}
//	@Inject
//    UserResource userResource; // Mocking the UserService dependency
//	
//	@InjectMock
//	 EntityManager entityManager;

//    @Test
//    public void testCreateUser() throws Exception {
//       
//        User user1 = new User(1L,"ninad","chanda",749883,LocalDate.now());
//        
//         Mockito.doNothing().when(user1).persist(user1);
//         Mockito.when(user1.isPersistent()).thenReturn(true);
//         
//         Response response = userResource.createUser(user1);
//         
//         assertNotNull(response);
//         assertNotNull(response.getEntity());
//         assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
//         
    
        //when(userResource.createUser(any(User.class))).thenReturn(Response.created(URI.create("/users/1")).build());
//        given()
//            .contentType(ContentType.JSON)
//            .body(user)
//        .when()
//            .post("/users/createUser")
//        .then()
//            .statusCode(201); // HTTP status code for created

      
  //  }
//}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//    @Test
//    public void testCreateUser() {
//        // Mock user data for testing
//        User user = new User();
//        user.setUserName("testUser");
//        // Perform a POST request to the createUser endpoint
//        Response response = given()
//            .contentType(ContentType.JSON)
//            .body(user)
//            .when()
//            .post("/createUser");
//        // Assert the response status code
//        response.then().statusCode(201); // HTTP 201 Created
//        // Check if the response body contains an "id" field and retrieve its value
//        int createdUserId = response.then().extract().path("id");
//        // Assert the value of the "id" field
//        response.then().body("id", equalTo(createdUserId));
//        // Clean up or additional assertions as needed
//    }
//    
//    @Test
//    public void testGetById() {
//        // Mock user data for testing
//        User mockUser = new User();
//        mockUser.setid(1);
//        mockUser.setUserName("testUser");
//
//        // Save the mock user to the database or in-memory storage
//        // You may use TestEntityManager or other mechanisms for testing persistence
//
//        // Perform a GET request to the getById endpoint
//        io.restassured.response.Response response = given()
//            .pathParam("id", mockUser.getid())
//            .when()
//            .get("/getById/{id}");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body("id", equalTo(mockUser.getid()));
//        response.then().body("userName", equalTo(mockUser.getUserName()));
//    }}
//    
//    @Test
//    public void testGetUserCount() {
//        // Mock the user count for testing
//        long mockUserCount = 5;
//
//        // Perform a GET request to the getUserCount endpoint
//        Response response = given()
//            .when()
//            .get("/userCount");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body(equalTo(" NUMBER OF USER IN DATBASE: " + mockUserCount));
//    }
//	
//	  @Test
//    public void testGetUsersInRange() {
//        // Mock query parameters for testing
//        String startDate = "2023-01-01";
//        String endDate = "2023-12-31";
//
//        // Perform a GET request to the getUsersInRange endpoint
//        Response response = given()
//            .queryParam("startDate", startDate)
//            .queryParam("endDate", endDate)
//            .when()
//            .get("/range");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body("size()", equalTo(0)); // Assuming no test data is available
//
//        // Optionally, you can assert other aspects of the response, such as the date range filter
//        // response.then().body("date", everyItem(isBetween(startDate, endDate)));
//    }
//	
//	  @Test
//    public void testGetCountOfRangeOfDate() {
//        // Mock query parameters for testing
//        String startDate = "2023-01-01";
//        String endDate = "2023-12-31";
//
//        // Perform a GET request to the getCountOfRangeOfDate endpoint
//        Response response = given()
//            .queryParam("startDate", startDate)
//            .queryParam("endDate", endDate)
//            .when()
//            .get("/getCountOfRangeOfDate");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body(equalTo("0")); // Assuming no test data is available
//
//        // Optionally, you can assert other aspects of the response, such as the date range filter
//        // response.then().body(equalTo(String.valueOf(expectedCount)));
//    }
//	
//	@Test
//    public void testGetAllUser() {
//        // Perform a GET request to the getAllUser endpoint
//        Response response = given()
//            .when()
//            .get("/users");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body("size()", hasSize(3)); // Assuming no test data is available
//
//        // Optionally, you can assert other aspects of the response
//        // response.then().body("[0].username", equalTo("expectedUsername"));
//    }}
//	
//	  @Test
//    public void testGetUsersInPage() {
//        // Mock query parameters for testing
//        int page = 1;
//        int pageSize = 2;
//
//        // Perform a GET request to the getUsersInPage endpoint
//        Response response = given()
//            .queryParam("page", page)
//            .queryParam("pageSize", pageSize)
//            .when()
//            .get("/page");
//
//        // Assert the response status code
//        response.then().statusCode(200); // HTTP 200 OK
//
//        // Assert the response body
//        response.then().body("size()", hasSize(0)); // Assuming no test data is available
//
//        // Optionally, you can assert other aspects of the response
//        // response.then().body("[0].username", equalTo("expectedUsername"));
//    }	

