package com.meudroz.backend_test_java.EmpresaDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "EmpresaDTO", description = "Dados da empresa")
public class EmpresaDTO {

    @Schema(description = "Nome da empresa", example = "JAVA TESTE Ltda")
    @NotBlank(message = "O nome da empresa é obrigatório")
    private String nome;

    @Schema(description = "CNPJ da empresa", example = "12345678000112")
    @NotBlank(message = "O cnpj da empresa é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve ter exatamente 14 dígitos numéricos.")
    private String cnpj;

    @Schema(description = "Endereço da empresa", example = "Rua do teste, 123")
    @NotBlank(message = "O endereço da empresa é obrigatório")
    private String endereco;

    // Construtor padrão
    public EmpresaDTO() {}

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
