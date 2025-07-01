package br.com.plataforma_rivalix_back.plataforma_rivalix.model.enums;

public enum ModalidadeEnum {
    SOLO("Solo"),
    DUPLA("Dupla"),
    EQUIPE("Equipe"); // Exemplo, se houver modalidade por equipe

    private final String nomeExibicao;

    ModalidadeEnum(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}