package com.gestaoensino.gestao_ensino.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)// Só inclua na representação se não for nulo
@Getter
@Builder
public class Problem {
	
	private Integer status;
	private LocalDateTime timestamp;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	private List<Object> objects;
	
	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		private String userMessage;
	}
	
	
	

}
