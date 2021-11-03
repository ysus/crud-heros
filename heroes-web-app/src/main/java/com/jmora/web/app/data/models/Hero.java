package com.jmora.web.app.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jmp
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Hero {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="hero_name")
	private String heroName;
	
	private String power;
	
	@Column(name="real_name")
	private String realName;
	
	
}
