package com.yuri;

import com.yuri.domain.Name;
import com.yuri.service.NameService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

@SpringBootApplication
public class TypeaheadBack {
	
	//@Value("${server.port}")
	//private String PORT;

	@Value("${JSON_FILE}")
	public String JSON_FILE;
	
	public static void main(String[] args) {
		SpringApplication.run(TypeaheadBack.class, args);
	}

	@SuppressWarnings("null")
	@Bean
	CommandLineRunner runnerName(NameService nameService){
	    return args -> {
        	System.out.println(JSON_FILE);
        	try (FileReader reader = new FileReader(JSON_FILE))
	        {
	        	JSONParser jsonParser = new JSONParser();
	        	JSONObject obj = (JSONObject)jsonParser.parse(reader);
	        	long count = 1;
		        for(Iterator<?> iterator = obj.keySet().iterator(); iterator.hasNext();) {
		        	  String key = (String) iterator.next();
		        	  long value = (long) obj.get(key);
		        	  Name name = new Name(count++, key, value);
		        	  nameService.addName(name);		        
		        }						    
		        System.out.println("Names Saved!");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    };
	}
}
