# ImageLite - Spring Boot + React + TypeScript + Docker

Este projeto é uma aplicação web que utiliza **Spring boot, React, TypeScript e Docker**. Esse projeto é um repositório de imagens, onde o usuário, após se autenticar, pode fazer o upload de novas imagens ou baixar imagens existentes.



## Tecnologias Utilizadas

- Java e Spring Boot (Backend)
- Postgres (Banco de dados)
- React e TypeScript (Frontend)
- Docker (Containerização)

### Pré-requisitos
- Docker
- Docker Compose

### Funcionalidades do Projeto
- Registro: Usuários podem se registrar fornecendo e-mail, nome, senha e confirmação de senha.
- Login: Usuários podem fazer login com e-mail e senha.
- Upload Image: O usuário, após logado, pode fazer o upload de imagens GIF, PNG ou JPEG para o servidor.
- Download Image: O usuário, após logado, pode fazer o download de imagens existentes no servidor.
- Search Image: O usuário, após logado, pode filtrar as imagens por nome ou tags atribuidas a ela, facilitando a pesquisa de imagens para download.

## Endpoints da API (Backend)

### `Documentação da API`

- **`POST /v1/users/register`**  
  Realiza o registro de um novo usuário. O corpo da requisição deve conter:
  ```json
  {
    "name": "User",
    "email": "user@example.com",
    "password": "userPassword"
  }
  ```

- **`POST /v1/users/auth`**  
  Realiza o login do usuário. O corpo da requisição deve conter:
  ```json
  {
    "email": "user@example.com",
    "password": "userPassword"
  }
  ```

- **`POST /v1/images`**  
  Realiza o cadastro de uma nova imagem. O corpo da requisição deve conter:
  ### **Headers:**
    ```http
    Content-Type: multipart/form-data
    ```

    ### **Campos do Formulário:**
    | Campo | Tipo | Descrição |
    |-------|------|-----------|
    | `file` | Arquivo (`.jpeg`, `.png`) | A imagem a ser enviada.|
    | `name` | String | Nome da imagem.|
    | `tags` | Lista de Strings | Tags associadas à imagem.|

    ### **Exemplo de Resposta**

    A resposta será um **status code 201** e um cabeçalho **`Location`** com a URL da imagem.

    **Código HTTP:** `201 Created`

    **Cabeçalhos da Resposta:**

    ```http
    Location: https://meuservidor.com/imagens/654321abc
    ```
    A URL no cabeçalho `Location` é a referência para acessar a imagem recém-cadastrada.


- **`GET /v1/images/:id`**  
  Faz a busca de uma imagem pelo seu id.  

  **Exemplo de URL**:  
  `POST /v1/images/1bdda2cf-9387-477a-80a7-ce98621e99fe`  

  **Exemplo de resposta**  
  Ao realizar a requisição no navegador, a resposta será o download do arquivo de imagem. O cabeçalho `Content-Disposition` será utilizado para sugerir o nome do arquivo e indicar que ele deve ser baixado.
      
    **Código HTTP:** `200 OK`

    **Cabeçalhos da Resposta:**

    ```http
    Content-Disposition: form-data; name="inline;filename="Imagem.PNG""; filename="Imagem.PNG"
    Content-Type: image/png
    ```
    O arquivo será baixado com o nome `Imagem.PNG`.

- **`GET /v1/images?extension=png&query=tag1`**  
  Faz uma requisição ao servidor, buscando por imagens que tenham a extensão e tags/nome passadas na url, por meio do extension e query, respectivamente.
  
  **Exemplo de URL**:  
  `GET /v1/images?extension=png`
  
  O servidor irá retornar todas as imagens que são do tipo png.

  `GET /v1/images?query=nat`  

  O servidor irá retornar todas as imagens que tenham uma tag ou nome começando com nat.

  `GET /v1/images?extension=png&query=nat`  
  
  O servidor irá retornar todas as imagens que são do tipo png e tenham uma uma tag ou nome começando com nat.

## Como Executar o Projeto

1. Clone o repositório:
  ```bash
  git clone https://github.com/JeielLucas/ImageLite.git
  ```
2. Navegue até a pasta do projeto.
```
cd ImageLite
```
3. Certifique-se de ter o Docker e Docker Compose instalados em sua máquina.
4. Na raiz do projeto, rode o comando:
    ```
    docker-compose up -d --build
    ```
5. Verifique se o container está rodando, utilize o seguinte comando:
```
docker-compose ps
```
6. Acesse o frontend pelo navegador em http://localhost:3000. O backend estará disponível em http://localhost:8080. Postgres estará rodando em http://localhost:5432. Para acessar o banco de dados, poderá usar o pgadmin que roda em http://localhost:15432.
### Observações:
- O login e senha para acessar o pgadmin e o postgres estão configurados no docker-compose.yml, podendo ser alterados.

## Exemplo de respostas
- **Exemplo de mensagens de erro**:

- "User already exists!": Quando o e-mail fornecido no cadastro já está registrado no sistema.
- "User not authorized": Quando o e-mail ou senha fornecidos são inválidos.
- "Missing required parameter: tags": Quando não é fornecida nenhuma tag ao fazer o upload da imagem.
- "Missing required parameter: name": Quando o nome não é fornecido ao fazer o upload da imagem.
- "Image not found": Quando um id inexistente é passado para procurar uma imagem.


#### Estilo de resposta
```
{
  "error: "Mensagem de erro"
}
```

- **Exemplo de mensagens de sucesso**:
- "accessToken": Quando o usuário faz o login, um token de acesso é retornado.
#### Estilo de resposta
```
{
	"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZUBnbWFpbC5jb20iLCJleHAiOjE3MzkyMDIzMTYsIm5hbWUiOiJUZXN0ZSJ9.tTJps7SMim1BmIpdaZvFi52akss___xKw9HSSn95uxY"
}
```
```
[]
```
```
[
	{
		"url": "http://localhost:8080/v1/images/c52a3c07-b1fe-421d-81d1-50720ebf89a3",
		"name": "Teste",
		"extension": "PNG",
		"size": 83582,
		"uploadDate": "10/02/2025"
	},
	{
		"url": "http://localhost:8080/v1/images/49da4984-64b2-4d62-82cb-5d34329dd7b9",
		"name": "TesteName",
		"extension": "PNG",
		"size": 83582,
		"uploadDate": "10/02/2025"
	}
]
```
Os dois últimos exemplos de resposta são para busca de imagens, utilizando extensão e query, podendo ter um retorno vazio [] ou retornando as imagens, igual no último exemplo.


## Screenshots

![App Screenshot](ImageLite.png)

