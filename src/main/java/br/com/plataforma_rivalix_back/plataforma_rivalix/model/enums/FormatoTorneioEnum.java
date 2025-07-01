package br.com.plataforma_rivalix_back.plataforma_rivalix.model.enums;

public enum FormatoTorneioEnum {
    ELIMINACAO_UNICA("Eliminação Única"), // Também conhecida como Eliminação Simples
    ELIMINACAO_DUPLA("Eliminação Dupla"),
    PONTOS_CORRIDOS("Pontos Corridos"),
    FASE_DE_GRUPOS_E_ELIMINACAO("Fase de Grupos + Eliminação"), // Se for combinado
    RANKING_POR_PONTOS("Ranking por Pontos");

    private final String nomeExibicao;

    FormatoTorneioEnum(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}