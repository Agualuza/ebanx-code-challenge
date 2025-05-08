# ebanx-code-challenge
Projeto em Kotlin usando Ktor que simula operações bancárias como depósito, saque e transferência entre contas. Desenvolvido como parte de um desafio técnico.

## Tecnologias
Kotlin

Ktor

Koin (Injeção de Dependência)

Jackson (Serialização JSON)

JUnit 5 + MockK (Testes)

Heroku (Deploy)

## Compilando o Projeto

Para compilar o projeto rodar

No Windows:

``gradlew clean fatJar``

No Unix/Mac:

``./gradlew clean fatJar``

Para rodar o projeto

``java -jar build/libs/ebanx-code-challenge-1.0-SNAPSHOT.jar``

## Rodando os testes

No Windows:

``gradlew test``

No Unix/Mac:

``./gradlew test``

## Repositório

https://github.com/Agualuza/ebanx-code-challenge

## Url para acessar

https://accounts-balance-f1d18498496e.herokuapp.com/

(lembrando que a rota / não existe então vai dar 404, lembre-se de utilizar as rotas válidas citadas abaixo)

## Endpoints

| Método | Caminho     | Descrição                                                   |
|--------|-------------|-------------------------------------------------------------|
| POST   | `/event`    | Processa um evento (depósito, saque ou transferência)       |
| GET    | `/balance`  | Retorna o saldo de uma conta usando o parâmetro `account_id` |
| POST   | `/reset`    | Reseta o estado da aplicação (zera as contas)     |

### Exemplo de Requisição POST `/event`

#### Depósito

```json
{
  "type": "deposit",
  "destination": 100,
  "amount": 10.0
}
```

#### Saque

```json
{
  "type": "withdraw",
  "origin": 100,
  "amount": 5.0
}
```

#### Transferência
```json
{
  "type": "transfer",
  "origin": 100,
  "destination": 200,
  "amount": 10.0
}
```

###  Exemplo de Requisição GET /balance?account_id=100

### Exemplo de Requisição POST /reset

Iago Agualuza
