package com.jmora.web.app.data.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroResponse {
	
	private Long id;
	private String heroName;
	private String power;
	private String realName;
	

}
