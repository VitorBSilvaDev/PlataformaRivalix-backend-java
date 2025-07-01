package br.com.plataforma_rivalix_back.plataforma_rivalix.model.enums;

public enum JogoEnum {
	Brawlhalla("Brawlhalla"),
	Fatal_fury_City_of_the_Wolves("Fatal Fury: City of the Wolves"),
	GBVSR("GBVSR"),
	Guilty_Gear_Strive("Guilty Gear Strive"),
	Mortal_Kombat_1("Mortal Kombat 1"),
	Street_Fighter_6("Street Fighter 6"),
	Tekken_8("Tekken 8");
	
	private final String nomeExibicao;
	
	JogoEnum(String nomeExibicao) {
	   this.nomeExibicao = nomeExibicao;
	}

	public String getNomeExibicao() {
	   return nomeExibicao;
	}
}
