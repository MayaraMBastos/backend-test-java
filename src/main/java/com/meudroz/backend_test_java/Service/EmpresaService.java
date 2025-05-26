package com.meudroz.backend_test_java.Service;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;

import com.meudroz.backend_test_java.Repository.EmpresaRepository;

import com.meudroz.backend_test_java.Utils.EmpresaValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService {


    @Autowired
    private final EmpresaRepository empresaRepository;
    @Autowired
    private final EmpresaValidator empresaValidator;


    public EmpresaService(EmpresaRepository empresaRepository, EmpresaValidator empresaValidator) {
        this.empresaRepository = empresaRepository;
        this.empresaValidator = empresaValidator;
    }


    public List<Map<String, Object>> listarEmpresas() {
        List<Map<String, Object>> empresas = empresaRepository.listarEmpresas();

        for (Map<String, Object> empresa : empresas) {
            //formatcao para efeito de visualizacao
            String cnpj = (String) empresa.get("cnpj");
            //empresa.put("cnpj", cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));
            empresa.put("cnpj", cnpj);
        }

        return empresas;
    }

// iria utilizar um encapsulamento de DTOs neste metodo, pois a resposta da requisicao GET (buscarEmpresaPorCnpj) espera dados percistidos do banco quanto mensagens de validacoes, erros e sucessos como resposta.
    public Map<String, Object> buscarPorCnpj(String cnpj) {

        String cnpjLimpo = empresaValidator.limparCnpj(cnpj);
        List<Map<String, Object>> resultado = empresaRepository.buscarPorCnpj(cnpjLimpo);

        if (resultado.isEmpty()) {
            return Map.of("erro", "Empresa não encontrada com o CNPJ fornecido.");
        }

        Map<String, Object> empresa = resultado.getFirst();

        //empresa.put("cnpj", cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));

        return empresa;
    }




    public Map<String, Object> cadastrarEmpresa(@Valid EmpresaDTO empresa) {
        Map<String, Object> response = new HashMap<>();

        String cnpjLimpo = empresaValidator.limparCnpj(empresa.getCnpj());

        boolean existe = empresaRepository.existeEmpresaPorCnpj(cnpjLimpo);
        if (existe) {
            response.put("erro", "Empresa já cadastrada com o CNPJ fornecido.");
            return response;
        }


        Map<String, Object> erros = empresaValidator.validarCadastroDeEmpresa(empresa);
        if (!erros.isEmpty()) {
            response.put("erros", erros);
            return response;
        }

        String telefoneLimpo = empresaValidator.limparTelefone(empresa.getTelefone());

        int linhasAfetadas = empresaRepository.inserirEmpresa(empresa.getNome(), cnpjLimpo, empresa.getEndereco(), telefoneLimpo);


        if (linhasAfetadas > 0) {
            response.put("mensagem", "Empresa cadastrada com sucesso.");
            response.put("linhasAfetadas", linhasAfetadas);
        } else {
            response.put("erro", "Falha ao cadastrar a empresa.");
        }

        return response;
    }



    public Map<String, Object> atualizarEmpresa(String cnpj, @Valid EmpresaDTO empresa) {
        Map<String, Object> response = new HashMap<>();


        String cnpjLimpo = empresaValidator.limparCnpj(cnpj);


        boolean empresaExiste = empresaRepository.existeEmpresaPorCnpj(cnpjLimpo);
        if (!empresaExiste) {
            response.put("erro", "Empresa não encontrada com o CNPJ fornecido.");
            return response;
        }


        Map<String, Object> erros = empresaValidator.validarCadastroDeEmpresa(empresa);
        if (!erros.isEmpty()) {
            response.put("erros", erros);
            return response;
        }


        String telefoneLimpo = empresaValidator.limparTelefone(empresa.getTelefone());
        empresa.setTelefone(telefoneLimpo); // Atualiza o telefone sanitizado no DTO
        empresa.setCnpj(cnpjLimpo);         // Atualiza o CNPJ sanitizado no DTO


        int linhasAfetadas = empresaRepository.atualizarEmpresaPorCnpj(
                empresa.getNome(),
                empresa.getEndereco(),
                empresa.getTelefone(),
                empresa.getCnpj()
        );

        if (linhasAfetadas > 0) {
            response.put("mensagem", "Empresa atualizada com sucesso.");
            response.put("linhasAfetadas", linhasAfetadas);
        } else {
            response.put("erro", "Erro ao atualizar a empresa.");
        }

        return response;
    }

    public Map<String, Object> deletarEmpresa(String cnpj) {
        Map<String, Object> response = new HashMap<>();
        String cnpjLimpo = empresaValidator.limparCnpj(cnpj);

        boolean existe = empresaRepository.existeEmpresaPorCnpj(cnpjLimpo);
        if (!existe) {
            response.put("erro", "Empresa não encontrada.");
            return response;
        }

        int linhasAfetadas = empresaRepository.deletarEmpresaPorCnpj(cnpjLimpo);
        if (linhasAfetadas > 0) {
            response.put("mensagem", "Empresa deletada com sucesso.");
        } else {
            response.put("erro", "Erro ao deletar a empresa.");
        }

        return response;
    }


}