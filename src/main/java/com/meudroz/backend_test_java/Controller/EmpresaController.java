package com.meudroz.backend_test_java.Controller;


import java.util.List;
import java.util.Map;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import com.meudroz.backend_test_java.Service.EmpresaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Endpoints para cadastro e consulta de empresas")
public class EmpresaController {


    private final EmpresaService empresaService;


    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @Operation(summary = "Listar todas as empresas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas cadastradas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(example = """
              {
                "nome": "JAVA TESTE Ltda",
                "cnpj": "12.345.678/0001-12",
                "endereco": "Rua do teste, 123"
              }
            """))))
    @GetMapping(produces = "application/json")
    public List<Map<String, Object>> listarEmpresas()  {
        return empresaService.listarEmpresas();
    }


    @Operation(summary = "Buscar uma empresa pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada ou não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                      {
                        "nome": "JAVA TESTE Ltda",
                        "cnpj": "12.345.678/0001-12",
                        "endereco": "Rua do teste, 123"
                      }
                    """)))
    })
    @GetMapping(value = "/{cnpj}", produces = "application/json")
    public Object buscarPorCnpj(@PathVariable String cnpj)  {
        return empresaService.buscarPorCnpj(cnpj);
    }


    @Operation(summary = "Cadastrar uma nova empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa cadastrada ou erro de validação", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                      {
                        "mensagem": "Empresa cadastrada com sucesso.",
                        "linhasAfetadas": 1
                      }
                    """)))
    })

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Map<String, Object> cadastrarEmpresa(@Valid @RequestBody EmpresaDTO empresa) {

            return empresaService.cadastrarEmpresa(empresa);

        }

    @Operation(summary = "Atualizar dados de uma empresa pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada ou erro de validação", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                      {
                        "mensagem": "Empresa atualizada com sucesso.",
                        "linhasAfetadas": 1
                      }
                    """)))
    })
    @PutMapping(value = "/{cnpj}", consumes = "application/json", produces = "application/json")
    public Map<String, Object> atualizarEmpresa(@PathVariable String cnpj, @Valid @RequestBody EmpresaDTO empresa) {

            return empresaService.atualizarEmpresa(cnpj, empresa);


    }
    @Operation(summary = "Deletar dados de uma empresa pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa deletada ou erro de validação", content = @Content(mediaType = "application/json", schema = @Schema(example = """
                      {
                        "mensagem": "Empresa deletada com sucesso.",
                        "linhasAfetadas": 1
                      }
                    """)))
    })
    @DeleteMapping(value = "/{cnpj}", produces = "application/json")
    public Map<String, Object> deletarEmpresa(@PathVariable String cnpj) {
        return empresaService.deletarEmpresa(cnpj);
    }
}
