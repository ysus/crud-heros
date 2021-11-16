/**
 * 
 */
package com.jmora.web.app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author jmp
 *
 */
@Data
public class HeroRequest {

	@NotBlank(message = "heroName mustn't be empty")
	@NotNull(message = "heroName is required")
	private String heroName;

	@NotBlank(message = "heroName mustn't be empty")
	@NotNull(message = "power is required")
	private String power;

	@NotBlank(message = "heroName mustn't be empty")
	@NotNull(message = "realName is required")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "realName must be a string")
	private String realName;

}
