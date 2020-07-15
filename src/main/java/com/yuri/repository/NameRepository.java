package com.yuri.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuri.domain.Name;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameRepository extends CrudRepository<Name, Long> {

	@Query("FROM Name WHERE UPPER(name) = ?1")
	Name findNameByName(String prefix);
	 
    @Query("FROM Name WHERE UPPER(name) LIKE ?#{[0].toUpperCase()}% ORDER BY times DESC, name ASC")
    List<Name> findAllNameByTimesDescAndNameAscFilteredByName(String prefix, Pageable pageable);    
    
    @Query("FROM Name ORDER BY times DESC, name ASC")
    List<Name> findAllNameByTimesDescAndNameAsc(Pageable pageable);        
}