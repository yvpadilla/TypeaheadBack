package com.yuri.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuri.exception.NameNotFoundException;
import com.yuri.domain.Name;
import com.yuri.service.NameService;

@RestController
@RequestMapping(path = "/typeahead")
public class NameController {
	
	@Autowired
	private NameService service;

	@Value("${SUGGESTION_NUMBER}")
	public String SUGGESTION_NUMBER;
	
	@SuppressWarnings("null")
	@GetMapping( value = "/{prefix}",
    	consumes = "application/json"
    )	
	public ResponseEntity<List<Name>> findAllNameByTimesDescAndNameAsc(@PathVariable String prefix) {
        String upperPrefix = prefix.toUpperCase();
        
    	List<Name> allName = new ArrayList<Name>();    	
    	int rowsQuantity = Integer.parseInt(SUGGESTION_NUMBER);
    	System.out.println("SUGGESTION_NUMBER : "+rowsQuantity);    	
    	
    	// EXACT SEARCH
    	// If name exists, it is put on first place
    	Name nam = service.findNameByName(upperPrefix);
    	if (nam!=null) {
    		allName.add(nam);
    	}
    	// PARTIAL SEARCH
    	// Names containing same string are added
    	service.findAllNameByTimesDescAndNameAscFilteredByName(rowsQuantity, upperPrefix).forEach(s -> {
    		// Avoid name found on first search
    		if (nam!=null) {
    			if (!nam.equals(s)) {
    				allName.add(s);
    			}	
    		} else {
    			allName.add(s);
    		}
    	});
   	
    	// SUGGESTED LIST
    	// In case there's no exact or partial matches, a suggested list is sent
    	if(allName.size()==0) {
        	service.findAllNameByTimesDescAndNameAsc(rowsQuantity).forEach(s -> {
       			allName.add(s);
        	});    		
    	}

		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);			
	    return new ResponseEntity<List<Name>>(allName, httpHeaders, HttpStatus.OK);    	
    }	
	
	
	@PostMapping( value = ""
	)	
    public ResponseEntity<Name> updateName(@RequestBody Name name) {
		String prefix = name.getName();
		Name nameFound = service.findNameByName(prefix);
		if (nameFound!=null) {
			nameFound = service.updateNameTimes(nameFound);
			final HttpHeaders httpHeaders= new HttpHeaders();
		    httpHeaders.setContentType(MediaType.APPLICATION_JSON);		
		    return new ResponseEntity<Name>(nameFound, httpHeaders, HttpStatus.OK);
		} else {
			throw new NameNotFoundException("Name : "+prefix);		
		}
    }		
}
