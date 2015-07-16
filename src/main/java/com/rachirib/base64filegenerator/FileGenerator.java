package com.rachirib.base64filegenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * 
 * @author ricardo.chiriboga
 *
 */
public class FileGenerator {
	
	

	/**
	 * Generate a File from a Base64 String in file-stream.txt
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		Path path = FileSystems.getDefault().getPath("./src/main/resources", "file-stream.txt");
		byte[] data = Base64.getDecoder().decode(Files.readAllBytes(path));
		
		try (OutputStream stream = new FileOutputStream("./CDA.zip")) {
		    stream.write(data);
		}
	}

}
