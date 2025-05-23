package com.meudroz.backend_test_java.Service;

import com.meudroz.backend_test_java.EmpresaDTO.EmpresaDTO;
import com.meudroz.backend_test_java.Repository.EmpresaRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService {


    private EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Map<String, Object>> listarEmpresas() {
        // Chama o repositório para buscar a lista de empresas
        List<Map<String, Object>> empresas = empresaRepository.listarEmpresas();

        // Lógica adicional: Caso necessário, formate os dados aqui.
        // Por exemplo, podemos mover a formatação do CNPJ para o Service.
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
