package com.meudroz.backend_test_java.EmpresaDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "OperacaoResponseDTO", description = "Resposta das operações realizadas na empresa")
public class OperacaoResponseDTO {

    @Schema(description = "Mensagem sobre o resultado da operação", example = "Empresa cadastrada com sucesso.")
    private String mensagem;

    @Schema(description = "Quantidade de linhas afetadas pela operação", example = "1")
    private int linhasAfetadas;

    // Construtor
    public OperacaoResponseDTO(String mensagem, int linhasAfetadas) {
        this.mensagem = mensagem;
        this.linhasAfetadas = linhasAfetadas;
    }

    // Getters e setters
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public int getLinhasAfetadas() { return linhasAfetadas; }
    public void setLinhasAfetadas(int linhasAfetadas) { this.linhasAfetadas = linhasAfetadas; }
}