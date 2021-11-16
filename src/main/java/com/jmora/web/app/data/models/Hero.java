package com.jmora.web.app.data.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author jmp
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Hero extends AbstractAuditableEntity<Long> implements Serializable {
	private static final long serialVersionUID = 3504071429122820038L;

	@Column(name="hero_name")
	private String heroName;
	
	private String power;
	
	@Column(name="real_name")
	private String realName;
}
