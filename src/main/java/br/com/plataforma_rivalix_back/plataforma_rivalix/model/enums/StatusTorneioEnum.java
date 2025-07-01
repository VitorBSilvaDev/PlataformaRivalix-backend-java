package br.com.plataforma_rivalix_back.plataforma_rivalix.model.enums;

public enum StatusTorneioEnum {
    INSCRICOES_ABERTAS("Inscrições Abertas"),
    EM_ANDAMENTO("Em Andamento"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado"),
    AGUARDANDO_INICIO("Aguardando Início"); // Opcional, para torneios já criados mas com inscrições fechadas

    private final String nomeExibicao;

    StatusTorneioEnum(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}