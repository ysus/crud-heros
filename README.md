# crud-heros
repositorio que contiene un proyecto srpring boot con java 11
# Hero Crud Rest Api
## Features
- Spring boot 2.5.6
- Java 11
- TDD
- Manejo de Optional y Streams
- Liquidbase
- Spring security (JWT)
- Swagger
- AOP Logging
- Spring Data Auditing
- ModelMapper
- Docker
- Integration test 
- Spring boot cache

## Steps

1.- Crear un usuario en el servicio /user/saveUser

```sh
Enviar el siguiente json:
{
    "username": "jesus",
    "password": "morales",
    "email": "moralespanfilo2@gmail.com",
    "roles": ["Admin","Manager"]
}
```

2.- Iniciar sesion en el servicio /user/loginUser

```sh
Enviar el siguiente json:
{
    "username": "jesus",
    "password": "morales"
}
```

3.- Agregar el token devuelto en el paso 2 y agregarlo al header Authorization, y realizar las siguientes operationces:

```sh
POST http://localhost:8080/api/heros
{
  "realName":"iron man",
  "power":"armadura",
  "heroName":"Tony Stark"
}

GET http://localhost:8080/api/heros

GET http://localhost:8080/api/heros/1

DELETE http://localhost:8080/api/heros/1

PUT http://localhost:8080/api/heros/1
{
  "id":1,
  "realName":"Hulk",
  "power":"super mega fuerza",
  "heroName":"Robert Bruce Banner"
}
