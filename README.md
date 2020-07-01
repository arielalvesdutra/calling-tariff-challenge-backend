# Calling Tariff Challenge

Projeto desenvolvido com Java 8, Spring Boot 2.3, Hibernate, Maven, MySQL, H2, Lombok e TDD.

## Principais URLs

| URL  | Tipo |  Descrição |
| ---- | ---- |---- |
| /ddd  | GET  | Retorna os DDDs cadastrados.

| /call-plans  | GET  | Retorna os planos de ligação cadastrados.
| /call-tariff-maps  | GET  | Retorna o mapa de tarifa por origem e destino de ligação.
| /call-records/calculate | POST  | Simula o cálculo de uma ligação.

### /call-records/calculate - POST

Exemplo de JSON para realizar a requisição:

```json
{
    "duration": 20 ,
    "originCode":  51,
    "destinationCode":  11,
    "callPlanUuid": "17dd8d60-f794-4b7e-b5dd-6c379ee7e44b"
}
```

Descrição dos campos:

| Campo  | Obrigatório |  Descrição |
| ---- | ---- |---- |
| duration  | Sim  | Duração em minutos da ligação
| originCode  | Não  | DDD de origem
| destinationCode  | Não  | DDD de destino
| callPlanUuid  | Não  | Uuid do plano para simular a consulta

## Estrutura

Descrição breve da estutura atual do projeto:

- `controllers`: controllers
- `services`: são reutilizem ao longo do projeto. Fazem comunicação com controllers, services, entidades e repositories. Envolvem questões do domínio;
- `repositories`: repositories;
- `entities`: entidades;
- `helpers`: classes e métodos que podem ser reutilizados no projeto e que não envolvem questões do domínio;
- `factories`: solução adotada para fabricar entidades. Não é uma solução permanente;
- `configs`: configurações do sistema, como CORS, Spring Security e inserção de dados essenciais;
- `strategies`: ainda não utilizado, MAS, deverá ser utilizado para tarefas como cálculos.

## Testes

Os serviços e entidadas da aplicação foram desenvolvidos com TDD. Apenas a controller Home e a controller CallRecord foram desenvolvidas com TDD.

Foram criados Testes de Unidade e Testes de Integração.

Para executar Testes de Unidade, basta executar:

```shell script
mvn clean test
```

Para executar Testes de Unidade e Testes de Integração, basta executar:

```shell script
mvn clean verify
```

## Instalação

**1 - Base de dados**

A aplicação utiliza o banco de dados MySQL.

É possível configurar as informações da base de dados no arquivo `application.properties` ou inserindo os dados da base de dados na execução do projeto com `-D` via terminal.

**2 - Compilação e execução**

A primeira forma de executar a aplicação, é utilizando o plugin do spring boot. Basta executar nas pasta raiz do projeto os seguintes comandos:

```shell script
mvn clean verify
mvn spring-boot:run
```

Outra forma de executar o projeto, é compilar ele e depois executar. Os comandos são:
```shell script
mvn clean verify
java -jar target/calling_tariff-0.1.0-SNAPSHOT.jar
```
