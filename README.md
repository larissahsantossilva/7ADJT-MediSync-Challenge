# 🏥 MediSync

Projeto desenvolvido como parte do **Tech Challenge - Fase 3**, com foco em criar uma solução backend moderna, segura e escalável para ambientes hospitalares. O sistema permite o **agendamento de consultas**, **gestão do histórico de pacientes** e **envio de notificações automáticas**, utilizando **comunicação assíncrona via RabbitMQ** e suporte a **GraphQL**.

## 👥 Integrantes

| Nome                     | RM       |
|--------------------------|----------|
| Guilherme Correia Martins | RM360403 |
| Larissah Santos da Silva | RM360669 |
| Felipe Eduardo Gomes     | RM360584 |
| Deyvid Kalbiak Martim    | RM359078 |
| Daniel Souza dos Santos  | RM360340 |

---

## 🧠 Tecnologias Utilizadas

- Java 21
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- RabbitMQ
- GraphQL + GraphiQL
- Flyway
- Swagger
- Postman

---

## 🧱 Arquitetura

A arquitetura segue o padrão **baseado em camadas**, separando bem as responsabilidades:

- `Controller`: lida com requisições e respostas
- `Service`: contém as regras de negócio
- `Repository`: acessa o banco de dados
- `Model`: representa as entidades do sistema

Além disso, o projeto está dividido em **dois módulos**:
- `APP`: módulo principal da aplicação (Producer RabbitMQ)
- `Notification`: responsável por consumir mensagens do RabbitMQ (Consumer)

---

## 🗃️ Banco de Dados

Banco utilizado: **PostgreSQL**  
Migrações gerenciadas com **Flyway**

---

## 🔗 API REST

Documentação disponível via Swagger:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Exemplos de Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET    | `/api/v1/pacientes` | Lista pacientes |
| POST   | `/api/v1/pacientes` | Cria paciente |
| PUT    | `/api/v1/pacientes/{id}` | Atualiza paciente |
| DELETE | `/api/v1/pacientes/{id}` | Remove paciente |
| ...    | ...      | ...       |

---

## 📡 GraphQL

URL de acesso local: [http://localhost:8080/graphql](http://localhost:8080/graphql)

### Exemplos de Queries:
```graphql
query {
  listarConsultas {
    id
    nomePaciente
    dataHora
    nomeMedico
  }
}
```
## 📬 Mensageria com RabbitMQ

O RabbitMQ foi implementado para **disparar eventos sempre que uma consulta é criada ou atualizada**. Isso garante uma comunicação assíncrona entre os serviços, permitindo que o módulo `Notification` envie mensagens.

## 🐳 Docker & Execução Local

Para garantir um ambiente de desenvolvimento consistente e fácil de subir, utilizamos o **Docker** com **Docker Compose**. Isso permite rodar toda a aplicação, banco de dados e serviços auxiliares (como RabbitMQ) com um simples comando.

### 🛠️ Passos para execução local

1. Clone o repositório na sua máquina:
2. Acesse a pasta do projeto:
3. Suba os containers:
4. Verifique se os containers estão rodando:
5. Quando terminar, derrube os containers:

### 📦 Serviços no Docker Compose

- **PostgreSQL** (Banco de dados relacional)
- **RabbitMQ** (Serviço de mensageria com painel acessível via http://localhost:15672)
- **App** (Aplicação principal com API REST + GraphQL)
- **Notificação** (Serviço separado que consome mensagens do RabbitMQ e envia notificações)

O Dockerfile das aplicações Java utiliza uma **multi-stage build**, garantindo imagens menores e otimizadas para produção.

## 🧪 Collections para Teste

Para facilitar os testes manuais da aplicação, foi criada uma coleção no **Postman** contendo os principais endpoints da API REST, incluindo exemplos de requisições `GET`, `POST`, `PUT` e `DELETE` para os recursos de pacientes, médicos e enfermeiros.

### 📥 Link para a Collection do Postman

Você pode acessar e importar a coleção através do link abaixo:

🔗 [Clique aqui para abrir a Collection no Postman](https://speeding-station-415931.postman.co/workspace/fiap~d52dd68e-4865-4739-92da-bc6c1068d122/collection/43173766-55416f52-7f1f-4114-bbcd-47303d929fca?action=share&creator=28466598)

---

## 📁 Repositório do Código

Todo o código-fonte do projeto está disponível no GitHub:

🔗 [Acessar Repositório no GitHub](https://github.com/larissahsantossilva/7ADJT-MediSync-Challenge)  

