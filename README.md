# E-Commerce API

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6.1.12-brightgreen)
![Clean Architecture](https://img.shields.io/badge/Clean%20Architecture-%231E88E5.svg)
![TDD](https://img.shields.io/badge/Test%20Driven%20Development-TDD-red)

## Descrição

Este projeto é uma API para um sistema de e-commerce desenvolvida em Java utilizando o Spring Framework. O projeto segue os princípios de **Clean Architecture**, permitindo uma separação clara de responsabilidades entre camadas e tornando o sistema altamente modular e escalável. Além disso, a metodologia **Test Driven Development (TDD)** é utilizada para garantir a qualidade e funcionalidade do código.

## Tecnologias

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Maven** como ferramenta de build
- **JPA** para persistência de dados
- **JUnit 5** para testes unitários
- **Mockito** para simulação de dependências em testes

## Práticas e Ferramentas
- **Testes** com JUnit e Mockito
- **Test Driven Development (TDD)**
- **Clean Architecture**
- **SOLID Principles**

## Estrutura do Projeto

A arquitetura do projeto segue os princípios da Clean Architecture, com as seguintes camadas principais:

- **Core (Domínio):** Contém as regras de negócios e as entidades do sistema.
- **Use Cases:** Implementação dos casos de uso, onde a lógica de negócios é aplicada.
- **Adapters (Interfaces):** Implementa as interfaces de comunicação com frameworks externos, como controllers HTTP e repositórios de persistência.
- **Frameworks (Infraestrutura):** Camada responsável por integrar o projeto com frameworks e tecnologias como Spring Boot, JPA, e outros.

### Estrutura de Pastas

```bash
src
├── main
│   ├── java
│   │   └── br.com.jonatas.ecommerce
│   │       ├── adapter         # Implementação de interfaces e gateways
│   │       ├── core            # Entidades e lógica de negócios
│   │       ├── gateway         # Interfaces de saída para repositórios
│   │       ├── usecases        # Casos de uso
│   └── resources
│       ├── application.properties
│       └── ...
└── test
    └── java
        └── br.com.jonatas.ecommerce
            ├── usecases        # Testes dos casos de uso
            └── ...
``` 

# Funcionalidades
Cadastro de Usuários: Permite que novos usuários sejam cadastrados no sistema.
Autenticação: Login e autenticação de usuários com tokens JWT.
Gerenciamento de Produtos: Criação, atualização, remoção e consulta de produtos.
Pedidos: Usuários podem criar e gerenciar pedidos de compra.
Pagamento: Integração com gateways de pagamento para processar transações.

Como Executar
Pré-requisitos

- Java 21
- Maven ou Gradle
- Docker (opcional, se utilizar contêineres de banco de dados)

# Executando o Projeto
Clone o repositório:

```bash
git clone https://github.com/seu-usuario/ecommerce-api.git
cd ecommerce-api
```

Compile e execute o projeto:

Usando Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Acesse a API no navegador ou via Postman:
[http://localhost:8080/api/v1](http://localhost:8080/api/v1)

Testes

Este projeto utiliza TDD (Test Driven Development). Os testes são implementados utilizando JUnit 5 e Mockito.
Executar Testes

Para rodar todos os testes unitários:

Usando Maven:

```bash
 mvn test
```