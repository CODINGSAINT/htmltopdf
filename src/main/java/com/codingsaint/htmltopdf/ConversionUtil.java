package com.codingsaint.htmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConversionUtil {
	/**
	 * Where File will be stored after converstion
	 */
	@Value("${outputDir}")
	private String outputDir;
	
	/**
	 * 
	 * @param url : URL of website to be converted to HTML
	 * @return
	 * @throws Exception
	 */
	public String generatePDFFromHTML(String url) throws Exception{
		//find the folder where wkhtml tool is kept
		File resourcesDirectory = new File("src/main/resources");
		String fileName= outputDir+"/"+new Date().getTime()+".pdf";
		ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "cd "+resourcesDirectory.getAbsolutePath()+"/wkhtmltox/bin && wkhtmltopdf " +url+" "+fileName);
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; 
	            }
	            System.out.println(line);
	        }
	        return fileName;
	    } 
	
	}
	
	


