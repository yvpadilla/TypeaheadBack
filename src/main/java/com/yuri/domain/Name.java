package com.yuri.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@Entity
// Structure of Name is used to contain name and times elements
public class Name {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Internally an Id is used but it won't be shown when exposing this class in JSON format
	@JsonIgnore
	private Long id;		
	
	private String name;	
	private long times;
	
	public Name() {}
}
