package com.clover.exception;

import com.clover.payload.ApiResponce;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GolbalExceptionHandler implements ExceptionMapper<ResourceNotFoundException> {

	
	@Override
	public Response toResponse(ResourceNotFoundException exception) {
		// TODO Auto-generated method stub
		
		String message = exception.getMessage();
		
		ApiResponce api=new ApiResponce();
		api.setMessage(message);
		api.isSuccess();
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(api)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
