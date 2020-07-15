package com.yuri.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@Entity
public class Name {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;		
	
	private String name;	
	private long times;
	
	public Name() {}
}
