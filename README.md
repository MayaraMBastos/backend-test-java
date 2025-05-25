## Anotações sobre o desenvolvimento do teste - Mayara M Bastos

Anotações sobre o desenvolvimento do teste e algumas instruções:

Docker: Utilizei build sem testes, para isso modifiquei os doc Dockerfile e docker-compose
1. para gerar o jar utilize: mvn clean install -DskipTests
2. para subir utilize: docker-compose up --build

Swagger: Utilizei o swagger para documentacao
1. Passar dados via URL com @PathVariable em algumas requisicoes, gerou problemas de formatação e exposição de dados senciveis na URL.
2. A formataçao do CNPJ deve ser somente de caracteres numericos ao realizar as requisições, apesar de ter utilizado formatacao no metodo limparCnpj em EmpresaValidator.
3. Para resolver a formatacao e exposicao da Variavel da requisicao no DELETE, adicionei 3 opções (text/plain ou application/json no corpo da requisicao) ou (application/json na url com @PathVariable) como exemplo de alternativas para lidar com dados.
4. PORTANTO no DELETE a variavel é passada como texto simples, para efeito de exemplo.

DTOs:
1. Pensei em implementar um encapsulamento de DTOs para lidar tanto com as respostas das requisições quanto com mensagens de validacoes, erros, sucesso e exeçoes, mas o uso de Map me pareceu mais simples e eficiente.
