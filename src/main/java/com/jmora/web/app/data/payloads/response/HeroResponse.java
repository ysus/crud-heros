package com.jmora.web.app.data.payloads.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroResponse implements Serializable {
	private static final long serialVersionUID = 3827692407234688559L;
	
	private Long id;
	private String heroName;
	private String power;
	private String realName;
	

}
