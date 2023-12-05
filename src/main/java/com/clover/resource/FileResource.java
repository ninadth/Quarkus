package com.clover.resource;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.clover.entity.FileEntity;
import com.clover.utility.FileEncryptionService;
import com.clover.utility.FileService;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/FileResource")
@Dependent
public class FileResource {

	
	@Inject
    EntityManager entityManager;

    @Inject
    FileEncryptionService fileEncryptionService;
    
    @Inject
    FileService fileService;
    
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public void uploadFile(byte[] fileContent) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileContent(fileContent);
        fileService.saveFile(fileEntity);
    }



    @GET
    @Path("/download/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("fileId") Long fileId) {
        FileEntity fileEntity = fileService.getFileById(fileId);

        if (fileEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            String fileName = "downloaded_file"; // You may get the original file name from the entity
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            return Response
                    .ok(fileEntity.getFileContent())
                    .header("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"")
                    .build();
        } catch (UnsupportedEncodingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Transactional
//    public Response uploadSecureFile(
//    		@FormDataParam("file") InputStream fileInputStream,
//            @FormParam("file") FormDataContentDisposition contentDispositionHeader,
//            @FormParam("encryptionKey") String encryptionKey
//    ) {
//        try {
//            byte[] fileContent = fileInputStream.readAllBytes();
//            byte[] encryptedContent = fileEncryptionService.encrypt(fileContent, encryptionKey);
//
//            EncryptedFileEntity encryptedFileEntity = new EncryptedFileEntity();
//            encryptedFileEntity.setFileName(contentDispositionHeader.getFileName());
//            encryptedFileEntity.setEncryptedContent(encryptedContent);
//
//            entityManager.persist(encryptedFileEntity);
//
//            return Response.status(Response.Status.CREATED).entity("File uploaded and encrypted successfully").build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("Error uploading and encrypting file: " + e.getMessage())
//                    .build();
//        }
//    }

//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    @Transactional
//    public Response downloadSecureFile(@PathParam("id") Long id, @QueryParam("decryptionKey") String decryptionKey) throws Exception {
//        EncryptedFileEntity encryptedFileEntity = entityManager.find(EncryptedFileEntity.class, id);
//
//        if (encryptedFileEntity == null) {
//            return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
//        }
//
//        byte[] encryptedContent = encryptedFileEntity.getEncryptedContent();
//        byte[] decryptedContent = fileEncryptionService.decrypt(encryptedContent, decryptionKey);
//
//        return Response.ok(decryptedContent)
//                .header("Content-Disposition", "inline")
//                .build();
//    }
}

