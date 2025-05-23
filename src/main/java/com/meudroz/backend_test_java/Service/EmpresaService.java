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
    private EmpresaRepository empresaRepository;
    @Autowired
    private EmpresaValidator empresaValidator;


    public EmpresaService(EmpresaRepository empresaRepository, EmpresaValidator empresaValidator) {
        this.empresaRepository = empresaRepository;
        this.empresaValidator = empresaValidator;
    }

    public List<Map<String, Object>> listarEmpresas() {
        List<Map<String, Object>> empresas = empresaRepository.listarEmpresas();

        for (Map<String, Object> empresa : empresas) {
            String cnpj = (String) empresa.get("cnpj");
            empresa.put("cnpj", cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));
        }

        return empresas;
    }

    public Map<String, Object> buscarPorCnpj(String cnpj) {
        List<Map<String, Object>> resultado = empresaRepository.buscarPorCnpj(cnpj);

        if (resultado.isEmpty()) {
            return Map.of("erro", "Empresa não encontrada com o CNPJ fornecido.");
        }

        Map<String, Object> empresa = resultado.get(0);

        String cnpjFormatado = ((String) empresa.get("cnpj"))
                .replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");

        empresa.put("cnpj", cnpjFormatado);

        return empresa;
    }


    public Map<String, Object> cadastrarEmpresa(@Valid EmpresaDTO empresa) {
        Map<String, Object> response = new HashMap<>();


        Map<String, Object> erros = empresaValidator.validarCadastroDeEmpresa(empresa);
        if (!erros.isEmpty()) {
            response.put("erros", erros);
            return response;
        }


        int linhasAfetadas = empresaRepository.inserirEmpresa(empresa.nome, empresa.cnpj, empresa.endereco);


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


        Map<String, Object> erros = empresaValidator.validarCadastroDeEmpresa(empresa);
        if (!erros.isEmpty()) {
            response.put("erros", erros);
            return response;
        }


        boolean empresaExiste = empresaRepository.existeEmpresaPorCnpj(cnpj);
        if (!empresaExiste) {
            response.put("erro", "Empresa não encontrada com o CNPJ fornecido.");
            return response;
        }


        int linhasAfetadas = empresaRepository.atualizarEmpresaPorCnpj(cnpj, empresa);
        if (linhasAfetadas > 0) {
            response.put("mensagem", "Empresa atualizada com sucesso.");
            response.put("linhasAfetadas", linhasAfetadas);
        } else {
            response.put("erro", "Erro ao atualizar a empresa.");
        }

        return response;
    }


}