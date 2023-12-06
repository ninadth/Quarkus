package com.clover.resource;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.clover.entity.FileEntity;
import com.clover.utility.FileService;
import com.clover.utility.FileUploadRequest;

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
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;


@Path("/FileResource")
@Dependent
public class FileResource {

	private static String UPLOAD_DIR = "D://quarkuslog";
	
	@Inject
    EntityManager entityManager;

    @Inject
    FileService fileService;
    
    @Inject
    FileUploadRequest request;
    
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public void uploadFile(byte[] fileContent) 
    {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileContent(fileContent);
        fileService.saveFile(fileEntity);
    }

    @GET
    @Path("/download/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("fileId") Long fileId) 
    {
        FileEntity fileEntity = fileService.getFileById(fileId);

        if (fileEntity == null)
        {
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
//    @Path("/uploadFiles")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.TEXT_PLAIN)
//    @Transactional
//    public void uploadFiles(FileUploadRequest request) {
//        List<String> base64EncodedContents = request.getFileContents();
//        for (String base64EncodedContent : base64EncodedContents) {
//            byte[] decodedContent = Base64.getDecoder().decode(base64EncodedContent);
//            
//            FileEntity fileEntity = new FileEntity();
//            fileEntity.setFileContent(decodedContent);
//            fileService.saveFile(fileEntity);
//        }
//    }
    
//    @POST
//    @Path("/uploadMultipleFiles")
//    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response uploadMultipleFiles(@MultipartForm FileUploadRequest request) {
//         InputStream[] files = request.getFiles();
//
//        if (files == null) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("No files provided").build();
//        }
//
//        // Process each file
//        for (InputStream inputPart : files) {
//            try {
//                InputStream inputStream = ((InputPart) inputPart).getBody(InputStream.class, null);
//                // Process the file content as needed
//                // For example, save the inputStream to a file or process it in memory
//            } catch (IOException e) {
//            	e.printStackTrace();
//                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing files").build();
//            }
//        }
//        return Response.ok("Files uploaded successfully").build();
//    }
        
        @GET
        @Path("/getAllFiles")
        @Produces(MediaType.APPLICATION_JSON)
        public List<FileEntity> getAllFiles() 
        {
            return fileService.getAllFiles();
        }
        
        
        @POST
        @Path("/files")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        public Response handleFileUploadForm(@MultipartForm MultipartFormDataInput input) {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            List<String> fileNames = new ArrayList<String>();

            List<InputPart> inputParts = uploadForm.get("file");
            System.out.println("inputParts size: " + inputParts.size());
            
            String fileName = null;
            for (InputPart inputPart : inputParts) {
                try {
                    MultivaluedMap<String, String> header = inputPart.getHeaders();
                    fileName = getFileName(header);
                    fileNames.add(fileName);
                    System.out.println("File Name: " + fileName);

                    StreamingOutput stream = output -> {
                        try (InputStream inputStream = inputPart.getBody(InputStream.class, null);
                             OutputStream outputStream = output) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    // Save the file using the streaming output
                    saveFile(fileName, stream);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String uploadedFileNames = String.join(", ", fileNames);
            return Response.ok().entity("All files " + uploadedFileNames + " successfully.").build();
        }

        private void saveFile(String fileName, StreamingOutput stream) {
            try {
                File customDir = new File(UPLOAD_DIR);
                fileName = customDir.getAbsolutePath() + File.separator + fileName;

                try (OutputStream outputStream = new FileOutputStream(fileName)) {
                    stream.write(outputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private String getFileName(MultivaluedMap<String, String> header) {
            String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
            for (String filename : contentDisposition) {
                if ((filename.trim().startsWith("filename"))) {
                    String[] name = filename.split("=");
                    String finalFileName = name[1].trim().replaceAll("\"", "");
                    return finalFileName;
                }
            }
            return "unknown";
        }
}
//        @POST
//        @Consumes(MediaType.MULTIPART_FORM_DATA)
//        @Operation(
//                summary = "Upload Files",
//                description = "Upload multiple files with base64-encoded content."
//        )
//        @APIResponse(responseCode = "200", description = "Files uploaded successfully.")
//        public void uploadFiles(
//                @Parameter(
//                        description = "List of base64-encoded file contents.",
//                        required = true,
//                        schema = @Schema(type = "array", items = @Schema(type = "string", format = "binary"))
//                )
//                @FormDataParam("fileContents") List<FormDataBodyPart> fileContents) {
//
//            for (FormDataBodyPart fileContent : fileContents) {
//                byte[] decodedContent = Base64.getDecoder().decode(fileContent.getValueAs(String.class));
//
//                FileEntity fileEntity = new FileEntity();
//                fileEntity.setFileContent(decodedContent);
//                fileService.saveFile(fileEntity);
//            }
        


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


