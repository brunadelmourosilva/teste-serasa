# Desafio técnico - Serasa Experian 

### Tecnologias utilizadas

* Java 21
* Spring Boot 3.2.1
* Spring Data JPA
* Spring Validation
* Autenticação/Autorização com Spring Security e JWT
* Maven
* H2 (banco de dados em memória)
* Lombok
* MapStruct
* OpenAPI (Swagger 3)
* OpenFeign (para requisições externas)
* Junit 5 / Mockito

---

### Como utilizar a aplicação?

* Clone o projeto para o diretório desejado
* Rode a aplicação com a IDE IntelliJ IDEA
* Para acessar os endpoints via Swagger: http://localhost:8080/swagger-ui/index.html#/
* Para acessar o banco de dados H2: http://localhost:8080/h2-console/
  
---

### Processo de autenticação/autorização dos endpoints

Ao executar a aplicação, dois usuários padrão, 'admin' e 'user', serão automaticamente criados. Após a inicialização da aplicação, você pode utilizar os comandos cURL (ex.: Postman) fornecidos para obter tokens de autenticação para esses usuários, cada um com permissões diferentes (roles ADMIN / USER).

**Tokens de Autenticação**

Para gerar um token de autenticação para o usuário 'admin', execute o seguinte comando cURL:
```
curl --location 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"admin@gmail.com",
    "password":"123"
}'
```

Para gerar um token de autenticação para o usuário 'user', execute o seguinte comando cURL:
```
curl --location 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"user@gmail.com",
    "password":"123"
}'
```

**Autorização**

Os tokens de autenticação obtidos podem ser utilizados para acessar os endpoints protegidos, de acordo com as regras definidas no escopo do teste técnico.
Certifique-se de incluir o token obtido na seção de autorização disponibilizada pelo Swagger, como mostra a imagem logo abaixo:

![image](https://github.com/brunadelmourosilva/teste-serasa/assets/61791877/07867096-1e42-4e8f-b807-8a0d52648641)

---

### Features adicionais

**Anotações Personalizadas**

1. @NoHyphen: 
A anotação @NoHyphen foi introduzida para personalizar a validação do campo de CEP em requisições. Ao utilizar esta anotação, o sistema garante que o caractere "-" não será incluído no campo de CEP.

2. @ScoreRange: 
A anotação @ScoreRange oferece uma validação personalizada para o campo "score" em requisições. Com essa anotação, garante-se que o valor do campo "score" esteja dentro da faixa de 0 a 1000.

**Exceções Personalizadas**

As exceções personalizadas foram implementadas e são tratadas pela classe ```GlobalExceptionHandler```. Tais exceções foram criadas para oferecer um gerenciamento granular e amigável de erros durante a execução da aplicação.
