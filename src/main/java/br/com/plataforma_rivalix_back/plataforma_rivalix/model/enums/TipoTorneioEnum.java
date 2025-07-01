package br.com.plataforma_rivalix_back.plataforma_rivalix.model.enums;

public enum TipoTorneioEnum {
    PRESENCIAL("Presencial"),
    ONLINE("Online");
    
    private final String nomeExibicao;

    TipoTorneioEnum(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}