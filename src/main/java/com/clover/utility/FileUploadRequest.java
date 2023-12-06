package com.clover.utility;

import java.io.InputStream;
import java.util.List;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class FileUploadRequest {

	private List<String> fileContents;

	 @FormParam("files")
	 @PartType(MediaType.APPLICATION_OCTET_STREAM)
	 public InputStream[] files;

	public InputStream[] getFiles() {
		return files;
	}

	public void setFiles(InputStream[] files) {
		this.files = files;
	}
	
	public List<String> getFileContents() {
		return fileContents;
	}

	public void setFileContents(List<String> fileContents) {
		this.fileContents = fileContents;
	}

	public FileUploadRequest(List<String> fileContents, InputStream[] files) {
		super();
		this.fileContents = fileContents;
		this.files = files;
	}

	public FileUploadRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

   

    
	

	
	
	
	
	
}
