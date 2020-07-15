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

    public List<Name> findAllNameByTimesDescAndNameAsc(int rowsQuantity) {
    	List<Name> allName = new ArrayList<Name>();
    	nameRepository.findAllNameByTimesDescAndNameAsc(new PageRequest(0,rowsQuantity)).forEach(s -> {
    			allName.add(s);
    	});    	
        return allName;
    }	
    
    public List<Name> findAllNameByTimesDescAndNameAscFilteredByName(int rowsQuantity, String prefix) {
        String upperPrefix = prefix.toUpperCase();        
    	List<Name> allName = new ArrayList<Name>();
    	nameRepository.findAllNameByTimesDescAndNameAscFilteredByName(upperPrefix, new PageRequest(0,rowsQuantity)).forEach(s -> {
    			allName.add(s);
    	});    	
        return allName;
    }	
 
    public void addName(Name nam) {
    	nameRepository.save(nam);
    }

    public Name updateNameTimes(Name nam) {
    	nam.setTimes(nam.getTimes()+1);
    	return nameRepository.save(nam);
    }
    
	public Name findNameByName(String prefix) {
		String upperPrefix = prefix.toUpperCase();
		return nameRepository.findNameByName(upperPrefix);
	}

}
