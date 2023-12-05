package com.clover.resource;


import java.net.URI;
import java.util.List;

import com.clover.entity.Employee;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employees")
public class EmployeeResource {

    @Inject
    EntityManager entityManager;
    
   
    @POST
    @Path("/create")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
    	
    	//System.out.println("gjjgugugii ================"+employee.getId().longValue());
    	
        boolean entity = entityManager.createNamedStoredProcedureQuery("insert_employee")
            .setParameter("p_empName", employee.getEmpName())
            .setParameter("p_empDept", employee.getEmpDept())
            .setParameter("p_empCast", employee.getEmpCast())
            .setParameter("p_empSalary", employee.getEmpSalary())
            .setParameter("p_empContact", employee.getEmpContact())
            .execute();
        
        System.out.println("***************************"+entity+"*****************************");
        
        return Response.created(URI.create("/Employee/" + employee.getEmpId())).build();
    }
  
    @POST
    @Path("/createMultipleEmployees")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMultipleEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            boolean entity = entityManager.createNamedStoredProcedureQuery("insert_employee")
                .setParameter("p_empName", employee.getEmpName())
                .setParameter("p_empDept", employee.getEmpDept())
                .setParameter("p_empCast", employee.getEmpCast())
                .setParameter("p_empSalary", employee.getEmpSalary())
                .setParameter("p_empContact", employee.getEmpContact())
                .execute();
            System.out.println("***************************" + entity + "*****************************");
        }

        return Response.created(URI.create("/employees")).build();
    }
}