package com.meudroz.backend_test_java.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmpresaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int inserirEmpresa(String nome, String cnpj, String endereco) {
        String sql = "INSERT INTO empresas (nome, cnpj, endereco) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, nome, cnpj, endereco);
    }

    public int atualizarEmpresa(String nome, String endereco, String cnpj) {
        String sql = "UPDATE empresas SET nome = ?, endereco = ? WHERE cnpj = ?";
        return jdbcTemplate.update(sql, nome, endereco, cnpj);
    }

    public List<Map<String, Object>> listarEmpresas() {
        String sql = "SELECT nome, cnpj, endereco FROM empresas";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> buscarPorCnpj(String cnpj) {
        String sql = "SELECT nome, cnpj, endereco FROM empresas WHERE cnpj = ?";
        return jdbcTemplate.queryForList(sql, cnpj);
    }



}
