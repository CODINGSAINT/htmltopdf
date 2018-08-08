# Welcome to Convert HTML to PDF using WK HTMLTool !

Hi!  This is a PoC project for Converting HTML to PDF using [WKHTML](https://wkhtmltopdf.org) tool 


# Tools  & Prerequesite

 1. Java 8
 2. [WKHTML](https://wkhtmltopdf.org) tool  : Kept at resource folder
 3.  [Spring Boot](http://spring.io) : Spring boot
 4. Maven

## Depoyment

Download the project .
Run mvn clean install
run `java -jar target/htmltopdf-0.0.1-SNAPSHOT.jar`  or `mvn spring-boot:run`

## Working
Once application is up 

 1. open http://localhost:8080 
 ![enter image description here](http://i67.tinypic.com/2usx6s1.jpg)
 2. Write URL you want to convert 
 4. click convert button

## How it works

### Controller

 1. In App Controller takes q as URL parameter and forwards to  convertionUtil utility. 
 2. Utility return converted file location .
 3.  File is converted to downloadable response and sent

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
### ConversionUtil  


#### generatePDFFromHTML

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
		
 


```
