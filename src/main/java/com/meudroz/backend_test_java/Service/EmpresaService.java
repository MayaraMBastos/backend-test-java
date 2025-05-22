package com.meudroz.backend_test_java.Service;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmpresaService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public EmpresaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> cadastrarEmpresa(EmpresaDTO empresa){
        Map<String, Object> response = new HashMap<>();

        String sql = "INSERT INTO empresas (nome, cnpj, endereco) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, empresa.nome, empresa.cnpj, empresa.endereco);
        response.put("mensagem", "Empresa cadastrada com sucesso.");
        response.put("linhasAfetadas", rows);
        return response;
    }

    public Map<String, Object> atualizarEmpresa(EmpresaDTO empresa){
        Map<String, Object> response = new HashMap<>();


        String sql = "UPDATE empresas SET nome = ?, endereco = ? WHERE cnpj = ?";
        int rows = jdbcTemplate.update(sql, empresa.nome, empresa.endereco, empresa.cnpj);

        if (rows == 0) {
            response.put("erro", "Nenhuma empresa encontrada com o CNPJ fornecido.");
            return response;
        }

        response.put("mensagem", "Empresa atualizada com sucesso.");
        response.put("linhasAfetadas", rows);
        return response;
    }
}
