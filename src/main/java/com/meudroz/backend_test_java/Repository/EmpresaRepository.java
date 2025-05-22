package com.meudroz.backend_test_java.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
