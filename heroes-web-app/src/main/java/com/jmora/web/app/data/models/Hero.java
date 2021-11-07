package com.jmora.web.app.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author jmp
 *
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Hero extends AbstractAuditable<ApplicationUser, Long> {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="hero_name")
	private String heroName;
	
	private String power;
	
	@Column(name="real_name")
	private String realName;
	
	
}
