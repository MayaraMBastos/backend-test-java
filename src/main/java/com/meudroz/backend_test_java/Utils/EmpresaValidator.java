package com.meudroz.backend_test_java.Utils;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;

import java.util.HashMap;
import java.util.Map;

public class EmpresaValidator {

    private Map<String, Object> response = new HashMap<>();

    public Map<String, Object> validarCadastroDeEmpresa(EmpresaDTO empresa) {
        if (empresa.nome == null || empresa.nome.trim().isEmpty()) {
            response.put("erro", "O nome é obrigatório.");
            return response;
        }
        if (empresa.nome.length() > 100) {
            response.put("erro", "O nome pode ter no máximo 100 caracteres.");
            return response;
        }

        if (empresa.cnpj == null || empresa.cnpj.trim().isEmpty()) {
            response.put("erro", "O CNPJ é obrigatório.");
            return response;
        }

        String cnpjLimpo = empresa.cnpj.replaceAll("[^0-9]", "");

        if (cnpjLimpo.length() < 14 || cnpjLimpo.length() > 14) {
            response.put("erro", "O CNPJ deve ter exatamente 14 dígitos numéricos.");
            return response;
        }

        if (empresa.endereco != null && empresa.endereco.length() > 200) {
            response.put("erro", "O endereço pode ter no máximo 200 caracteres.");
            return response;
        }
        return response;
    }
}

