package com.meudroz.backend_test_java.EmpresaDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EmpresaResponseDTO", description = "Detalhes da empresa para resposta")
public class EmpresaResponseDTO {

    @Schema(description = "Nome da empresa", example = "JAVA TESTE Ltda")
    private String nome;

    @Schema(description = "CNPJ da empresa", example = "12345678000112")
    private String cnpj;

    @Schema(description = "Endereço da empresa", example = "Rua do teste, 123")
    private String endereco;

    // Construtor
    public EmpresaResponseDTO(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}