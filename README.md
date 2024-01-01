# Desafio técnico - Serasa Experian 

### Tecnologias utilizadas no back-end

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
* Junit 5 e Mockito

---

### Como utilizar a aplicação?

* 1 - Clone o projeto para o diretório desejado
* 2 - Rode a aplicação com a IDE IntelliJ IDEA
* 3 - Para acessar os endpoints via swagger: http://localhost:8080/swagger-ui/index.html#/
* 4 - Para acessar o banco de dados H2: http://localhost:8080/h2-console/
  
---

### Processo de autenticação/autorização dos endpoints

```
curl --location 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"admin@gmail.com",
    "password":"123"
}'
```

```
curl --location 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"user@gmail.com",
    "password":"123"
}'
```
