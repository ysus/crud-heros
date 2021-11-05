/**
 * 
 */
package com.jmora.web.app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Administrador
 *
 */
@Data
public class HeroRequest {
	
	@NotBlank
    @NotNull
	private String heroName;
	
	@NotBlank
    @NotNull
	private String power;
	
	@NotBlank
    @NotNull
	private String realName;

}
