package br.gov.pge.modulos.inovacao.entity;

public enum EtapaInovacao {
    IDENTIFICACAO("Identificação", 1, "Levantamento de problemas e oportunidades de melhoria"),
    IDEACAO("Ideação", 2, "Geração e desenvolvimento de ideias criativas"),
    SELECAO("Seleção", 3, "Avaliação e priorização das ideias mais promissoras"),
    PROTOTIPAGEM("Prototipagem", 4, "Desenvolvimento de protótipos e provas de conceito"),
    SANDBOX("Sandbox Regulatório", 5, "Testes controlados em ambiente seguro"),
    AMPLIACAO("Ampliação", 6, "Expansão da solução para áreas piloto"),
    EFETIVACAO("Efetivação", 7, "Implementação definitiva e institucionalização");
    
    private final String descricao;
    private final Integer ordem;
    private final String objetivo;
    
    EtapaInovacao(String descricao, Integer ordem, String objetivo) {
        this.descricao = descricao;
        this.ordem = ordem;
        this.objetivo = objetivo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public Integer getOrdem() {
        return ordem;
    }
    
    public String getObjetivo() {
        return objetivo;
    }
}
