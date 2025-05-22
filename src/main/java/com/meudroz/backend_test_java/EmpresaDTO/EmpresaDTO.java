package com.meudroz.backend_test_java.EmpresaDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EmpresaDTO", description = "Dados da empresa")
public class EmpresaDTO {
    @Schema(description = "Nome da empresa", example = "JAVA TESTE Ltda")
    public String nome;

    @Schema(description = "CNPJ da empresa", example = "12345678000112")
    public String cnpj;

    @Schema(description = "Endereço da empresa", example = "Rua do teste, 123")
    public String endereco;
}
