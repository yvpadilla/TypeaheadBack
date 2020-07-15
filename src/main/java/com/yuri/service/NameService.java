package com.yuri.service;

import com.yuri.domain.Name;
import com.yuri.repository.NameRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class NameService {

    private NameRepository nameRepository;
    
    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    // Provide a list of names ordered by times (descending) and names (ascending)
    // A filter by a rows quantity is applied to the list
    public List<Name> findAllNameByTimesDescAndNameAsc(int rowsQuantity) {
    	List<Name> allName = new ArrayList<Name>();
    	nameRepository.findAllNameByTimesDescAndNameAsc(new PageRequest(0,rowsQuantity)).forEach(s -> {
    			allName.add(s);
    	});    	
        return allName;
    }	
    
    // Provide a list of names which name begin by a prefix given as a parameter, this list is 
    // ordered by times (descending) and names (ascending).
    // A filter by a rows quantity is applied to the list    
    public List<Name> findAllNameByTimesDescAndNameAscFilteredByName(int rowsQuantity, String prefix) {
    	// "prefix" parameter is taken to an upper value. 
        String upperPrefix = prefix.toUpperCase();        
    	List<Name> allName = new ArrayList<Name>();
    	nameRepository.findAllNameByTimesDescAndNameAscFilteredByName(upperPrefix, new PageRequest(0,rowsQuantity)).forEach(s -> {
    			allName.add(s);
    	});    	
        return allName;
    }	
 
    // Insert a new name
    public void addName(Name nam) {
    	nameRepository.save(nam);
    }

    // Update name, incrementing times value by 1
    public Name updateNameTimes(Name nam) {
    	nam.setTimes(nam.getTimes()+1);
    	return nameRepository.save(nam);
    }
    
    // Provide a name if this is a result of an exact search made by a prefix given
	public Name findNameByName(String prefix) {
		// "prefix" parameter is taken to an upper value
		String upperPrefix = prefix.toUpperCase();
		return nameRepository.findNameByName(upperPrefix);
	}

}
