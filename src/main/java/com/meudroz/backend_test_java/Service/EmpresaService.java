package com.meudroz.backend_test_java.Service;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import com.meudroz.backend_test_java.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmpresaService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private EmpresaRepository empresaRepository;

    public EmpresaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> cadastrarEmpresa(EmpresaDTO empresa){
        Map<String, Object> response = new HashMap<>();
        int rows = empresaRepository.inserirEmpresa(empresa.nome, empresa.cnpj, empresa.endereco);

        response.put("mensagem", "Empresa cadastrada com sucesso.");
        response.put("linhasAfetadas", rows);

        return response;
    }

    public Map<String, Object> atualizarEmpresa(EmpresaDTO empresa){
        Map<String, Object> response = new HashMap<>();


        int rows = empresaRepository.atualizarEmpresa(empresa.nome, empresa.endereco, empresa.cnpj);

        if (rows == 0) {
            response.put("erro", "Nenhuma empresa encontrada com o CNPJ fornecido.");
            return response;
        }

        response.put("mensagem", "Empresa atualizada com sucesso.");
        response.put("linhasAfetadas", rows);
        return response;
    }
}
