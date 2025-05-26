package com.meudroz.backend_test_java.Utils;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmpresaValidator {


    public Map<String, Object> validarCadastroDeEmpresa(EmpresaDTO empresa) {

        Map<String, Object> response = new HashMap<>(); // Instância local do Map

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

        if (empresa.getTelefone() == null || empresa.getTelefone().trim().isEmpty()) {
            response.put("erro", "O numero de telefone é obrigatório.");
            return response;
        }

        String telefoneLimpo = limparTelefone(empresa.getTelefone().trim());

        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 15) {
            response.put("erro", "O numero de telefone deve conter o DDD ou DDI e estar no formato correto, exemplo: +5511999999999 ou 11 99999-9999.");
            return response;
        }


        String cnpjLimpo = limparCnpj(empresa.getCnpj());


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

    public String limparCnpj(String cnpj) {
        // Decodificar a URL caso ela venha codificada (ex: %2F para /)
        String cnpjDecodificado = URLDecoder.decode(cnpj, StandardCharsets.UTF_8);

        // Remover tudo que não for dígito (0-9)
        return cnpjDecodificado.replaceAll("[^0-9]", "");
    }

    public String limparTelefone(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

}

