# Alimenta+

Uma plataforma comunitária que conecta pessoas e produtores que possuem excedentes de alimentos a pessoas interessadas em recebê-los gratuitamente.

## Sobre o projeto

O Alimenta+ surgiu a partir do problema do desperdício de alimentos que ainda estão próprios para consumo.

Muitas vezes, pessoas, produtores e estabelecimentos possuem alimentos que não serão utilizados ou comercializados, mesmo estando em boas condições. Um exemplo são frutas e outros alimentos que acabam sendo descartados por não estarem dentro de um padrão visual, mesmo podendo ser consumidos normalmente.

A ideia do projeto é criar uma plataforma que facilite a conexão entre quem possui esses alimentos excedentes e quem tem interesse em recebê-los gratuitamente.

## Objetivo

O objetivo do Alimenta+ é facilitar o compartilhamento de alimentos excedentes dentro da comunidade, evitando que alimentos próprios para consumo sejam desperdiçados.

Na primeira versão da PoC, o sistema permite:

- cadastrar usuários;
- cadastrar ofertas de alimentos;
- visualizar ofertas disponíveis;
- demonstrar interesse em uma oferta;
- registrar solicitações;
- aceitar ou recusar solicitações;
- controlar o status das ofertas e solicitações.

## ODS

O projeto está relacionado ao:

**ODS 2 - Fome Zero e Agricultura Sustentável**

A escolha do ODS 2 está relacionada à proposta de facilitar o acesso a alimentos e contribuir para o melhor aproveitamento de alimentos excedentes.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.0.8
- Spring MVC
- Thymeleaf
- MongoDB
- Spring Data MongoDB
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Git e GitHub

## Banco de dados

O projeto utiliza o **MongoDB**, um banco de dados NoSQL.

As principais coleções utilizadas são:

- `usuarios`
- `ofertas`
- `solicitacoes`

Durante o desenvolvimento local, foi utilizado o banco `test`.

## Como executar

### Pré-requisitos

É necessário ter instalado:

- Java 21 ou superior
- MongoDB
- Git

O projeto possui Maven Wrapper, então não é necessário instalar o Maven separadamente.

### Executando o projeto

Primeiro, certifique-se de que o MongoDB está em execução.

Depois, abra o terminal na pasta do projeto e execute:

```powershell
.\mvnw.cmd spring-boot:run