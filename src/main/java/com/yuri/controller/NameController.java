package com.yuri.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/typeahead")
public class NameController {
	
	@Autowired
	private NameService service;

	@Value("${SUGGESTION_NUMBER}")
	public String SUGGESTION_NUMBER;
	
	@SuppressWarnings("null")
	@GetMapping("/{prefix}")
    public List<Name> findAllNameByTimesDescAndNameAsc(@PathVariable String prefix) {
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
    		//if (!s.equals(nam)) {
    				//nam!=null && !s.getName().toString().equals(nam.getName().toString()))
    			allName.add(s);
    		//}
    	});

    	if (nam!=null) {    	
    		allName.remove(rowsQuantity);
    	}
    	
    	// SUGGESTED LIST
    	// In case there's no exact or partial matches, a suggested list is sent
    	if(allName.size()==0) {
        	service.findAllNameByTimesDescAndNameAsc(rowsQuantity).forEach(s -> {
       			allName.add(s);
        	});    		
    	}
    	
        return allName;
    }	
	
	@PostMapping("")
    public Name updateName(@RequestBody Name name) {
		String prefix = name.getName();
		Name nameFound = service.findNameByName(prefix);
		if (nameFound!=null) {
			return service.updateNameTimes(nameFound);
		} else {
			throw new NameNotFoundException("Name : "+prefix);		
			//return null;
		}
    }		
}
