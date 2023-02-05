package com.gestaoensino.gestao_ensino.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	//Tipo de Enum (Complemento da uri, título)
	PARAMETRO_INVALIDO ("/parametro-invalido", "Parâmetro Inválido"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema","Erro de sistema"),
	RECURSO_NAO_ENCONTRADO ("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL ("/mensagem-incompreensivel","Mensagem Incompreensível");
	
	
	private String title;
	private String uri;
	
	ProblemType (String path, String title){
		this.title=title;
		this.uri="https://algafood.com.br"+path;
	}

}
