package com.codingsaint.htmltopdf;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 
 * @author Kumar Pallav
 *
 */
@Controller
public class AppController {
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@GetMapping("/")
	public String index(){
		return "index";
		
	}
	/**
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/html/to/pdf")
	public ResponseEntity<Resource>  convertToPdf(@RequestParam ("q") String url,HttpServletRequest request) throws Exception {
		String generatedFile=conversionUtil.generatePDFFromHTML(url);
		File pdfFile=new File(generatedFile);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));
		return ResponseEntity.ok()
				.header("Content-Disposition", String.format("inline; filename=\"" + pdfFile.getName() + "\""))
	            .contentLength(pdfFile.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);		
	}
	
}
