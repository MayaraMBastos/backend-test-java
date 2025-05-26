package com.meudroz.backend_test_java.Repository;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmpresaRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public EmpresaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int inserirEmpresa(String nome, String cnpj, String endereco, String telefone) {
        String sql = "INSERT INTO empresas (nome, cnpj, endereco, telefone) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, nome, cnpj, endereco);
    }


    public boolean existeEmpresaPorCnpj(String cnpj) {
        String sql = "SELECT COUNT(*) FROM empresas WHERE cnpj = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cnpj);
        return count != null && count > 0;
    }


    public int atualizarEmpresaPorCnpj(String cnpj, EmpresaDTO empresa) {
        String sql = "UPDATE empresas SET nome = ?, endereco = ?, telefone = ? WHERE cnpj = ?";
        return jdbcTemplate.update(sql, empresa.getNome(), empresa.getEndereco(), cnpj);
    }


    public List<Map<String, Object>> listarEmpresas() {
        String sql = "SELECT nome, cnpj, endereco, telefone FROM empresas";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> buscarPorCnpj(String cnpj) {
        String sql = "SELECT nome, cnpj, endereco, telefone FROM empresas WHERE cnpj = ?";
        return jdbcTemplate.queryForList(sql, cnpj);
    }

    public int deletarEmpresaPorCnpj(String cnpj) {
        String sql = "DELETE FROM empresas WHERE cnpj = ?";
        return jdbcTemplate.update(sql, cnpj);
    }



}
