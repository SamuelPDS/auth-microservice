# Auth Service API

## Sobre 

Este projeto é um microserviço de autenticação desenvolvido com Spring Boot e Spring Security utilizando JWT (JSON Web Token). Ele foi projetado para gerenciar a autenticação e autorização de usuários em uma arquitetura de microserviços.

## Funcionalidades Principais
- Criação de Usuário: Permite que novos usuários se registrem no sistema.
- Login: Realiza a autenticação de usuários, gerando um token JWT para acesso seguro a outros serviços.
- Recuperação de Senha: Fornece um mecanismo para que os usuários possam recuperar suas senhas esquecidas.

## Instalação
Para a instalação, basta ter um IDE de sua preferência, carregar as dependências do Spring, que no caso está sendo gerenciado pelo Maven.

``` bash
Exemplo de como é o código da biblioteca(abaixo o do JTW):

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
```

### Versões
- Spring 3.3.0
- Java 17
- Git
