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

	// Environment variable containing maximum quantity of elements to show 
	@Value("${SUGGESTION_NUMBER}")
	public String SUGGESTION_NUMBER;
	
	// FUNCTIONALITY: Give back n (defined by SUGGESTION_NUMBER) a list of elements 
	//				  most near to prefix received as parameter
	@SuppressWarnings("null")
	@GetMapping("/{prefix}")
    public List<Name> findAllNameByTimesDescAndNameAsc(@PathVariable String prefix) {
        String upperPrefix = prefix.toUpperCase();
        
    	List<Name> allName = new ArrayList<Name>();    	
    	// Env Var SUGGESTION_NUMBER limits elements for showing
    	int rowsQuantity = Integer.parseInt(SUGGESTION_NUMBER);    	
    	
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
    	
        return allName;
    }	
	
	// FUNCTIONALITY: Increment name's weigth (times) which is used as priority
	//				  when a list of elements is elaborated
	@PostMapping("")
    public Name updateName(@RequestBody Name name) {
		String prefix = name.getName();
		// SEARCH
		// In case name is found, times is incremented by 1
		Name nameFound = service.findNameByName(prefix);
		if (nameFound!=null) {
			return service.updateNameTimes(nameFound);
		// otherwise, error 400 is shown
		} else {
			throw new NameNotFoundException("Name : "+prefix);		
		}
    }		
}
