package com.meudroz.backend_test_java.Utils;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmpresaValidator {

    private final Map<String, Object> response = new HashMap<>();

    public Map<String, Object> validarCadastroDeEmpresa(EmpresaDTO empresa) {
        if (empresa.getNome() == null || empresa.getNome().trim().isEmpty()) {
            response.put("erro", "O nome é obrigatório.");
            return response;
        }
        if (empresa.getNome().length() > 100) {
            response.put("erro", "O nome pode ter no máximo 100 caracteres.");
            return response;
        }

        if (empresa.getCnpj() == null || empresa.getCnpj().trim().isEmpty()) {
            response.put("erro", "O CNPJ é obrigatório.");
            return response;
        }

        if (empresa.getEndereco() == null || empresa.getEndereco().isEmpty()) {
            response.put("erro", "O endereço é obrigatório.");
            return response;
        }

        String cnpjLimpo = empresa.getCnpj().replaceAll("[^0-9]", "");

        if (cnpjLimpo.length() != 14) {
            response.put("erro", "O CNPJ deve ter exatamente 14 dígitos numéricos.");
            return response;
        }

        if (empresa.getEndereco() != null && empresa.getEndereco().length() > 200) {
            response.put("erro", "O endereço pode ter no máximo 200 caracteres.");
            return response;
        }
        return response;
    }
}

