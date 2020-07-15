package com.yuri.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuri.domain.Name;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameRepository extends CrudRepository<Name, Long> {
	// Exact search by name. Name found is received as result otherwise result is null.
	// "prefix" paramteer and value of evaluated name are both upper values, 
	// hence query is non case sensitive
	@Query("FROM Name WHERE UPPER(name) = ?1")
	Name findNameByName(String prefix);
	 
	// Query for names whose name begins by prefix received as a parameter and
	// resultant list is ordered by times (ascending order) and name (descending order)
	// "pageable" parameter allows to filter resultant list. 
	// "prefix" paramteer and value of evaluated name are both upper values,
	// hence query is non case sensitive
    @Query("FROM Name WHERE UPPER(name) LIKE ?#{[0].toUpperCase()}% ORDER BY times DESC, name ASC")
    List<Name> findAllNameByTimesDescAndNameAscFilteredByName(String prefix, Pageable pageable);    
    
    // Query for all names and
    // resultant list is ordered by times (ascending order) and name (descending order)
    // "pageable" parameter allows to filter resultant list 
    @Query("FROM Name ORDER BY times DESC, name ASC")
    List<Name> findAllNameByTimesDescAndNameAsc(Pageable pageable);        
}