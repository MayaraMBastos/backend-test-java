package com.meudroz.backend_test_java.Controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import com.meudroz.backend_test_java.EmpresaDTO.EmpresaResponseDTO;
import com.meudroz.backend_test_java.EmpresaDTO.OperacaoResponseDTO;
import com.meudroz.backend_test_java.Service.EmpresaService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    @ApiResponse(responseCode = "200", description = "Lista de empresas cadastradas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmpresaResponseDTO.class))))
    @GetMapping(produces = "application/json")
    public List<EmpresaResponseDTO> listarEmpresas() {
        return empresaService.listarEmpresas().stream()
                .map(data -> new EmpresaResponseDTO(
                        (String) data.getNome(),
                        (String) data.getCnpj(),
                        (String) data.getEndereco()
                ))
                .collect(Collectors.toList());
    }


//    @Operation(summary = "Buscar uma empresa pelo CNPJ")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Empresa encontrada ou não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(example = """
//                      {
//                        "nome": "JAVA TESTE Ltda",
//                        "cnpj": "12.345.678/0001-12",
//                        "endereco": "Rua do teste, 123"
//                      }
//                    """)))
//    })
//    @GetMapping(value = "/{cnpj}", produces = "application/json")
//    public Object buscarPorCnpj(@PathVariable String cnpj)  {
//        return empresaService.buscarPorCnpj(cnpj);
//    }

    @Operation(summary = "Buscar uma empresa pelo CNPJ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Empresa encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Empresa não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OperacaoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = """
                                {
                                  "mensagem": "Ocorreu um erro interno.",
                                  "linhasAfetadas": 0
                                }
                            """)
                    )
            )
    })
    @GetMapping(value = "/{cnpj}", produces = "application/json")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
        try {
            // Chama o serviço para buscar os dados da empresa
            var result = empresaService.buscarPorCnpj(cnpj);

            // Caso o resultado seja vazio ou nulo, retorna OperacaoResponseDTO com status 404
            if (result == null || ((Map<String, Object>) result).isEmpty()) {
                return ResponseEntity.status(404).body(
                        new OperacaoResponseDTO("Empresa não encontrada com o CNPJ fornecido.", 0)
                );
            }

            // Se houver resultado, converte para EmpresaResponseDTO
            EmpresaResponseDTO empresaResponse = new EmpresaResponseDTO(
                    (String) ((Map<String, Object>) result).get("nome"),
                    (String) ((Map<String, Object>) result).get("cnpj"),
                    (String) ((Map<String, Object>) result).get("endereco")
            );

            // Retorna o objeto com status 200
            return ResponseEntity.ok(empresaResponse);

        } catch (Exception ex) {
            // Tratamento genérico para captura de erros inesperados
            return ResponseEntity.status(500).body(
                    new OperacaoResponseDTO("Ocorreu um erro interno.", 0)
            );
        }
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
