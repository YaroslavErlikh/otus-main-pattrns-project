# Проектная работа курса OTUS Архитектура и шаблоны проектирования

[![codecov](https://codecov.io/gh/YaroslavErlikh/otus-main-pattrns-project/branch/main/graph/badge.svg?token=FQRV0XJVX0)](https://codecov.io/gh/YaroslavErlikh/otus-main-pattrns-project)

## Описание

Умные ссылки.

Пользователь переходит по ссылке. В зависимости от набора правил, которые могут включать интервалы времени,
местоположение пользователя, устройство, браузер и др. система выполняет редирект на урл, соответствующий сработавшему
правилу.

## Архитектура

![arch.jpg](doc%2Farch.png)

## Работа с приложением

### Требования системы

Должно быть установлено:

- Java версии 17
- maven версии 3.8.1
- Docker

### Сборка системы

Команда сборки модулей:

```bash
mvn clean install
```

### Запуск

#### Docker

Запуск в Docker:

```bash
 docker-compose up -d --build
```

Первый запуск системы подготавливает образы Docker, что занимает некоторое время

#### Standalone

Перед запуском необходимо поднять сервер MongoDB.

Система также может быть запущена помодульно в произвольной последовательности с профилем `dev`:

```bash
java -jar ./auth/service/target/auth-service.jar --spring.profiles.active=dev
java -jar ./editor/service/target/editor-service.jar --spring.profiles.active=dev
java -jar ./jwt/service/target/jwt-service.jar --spring.profiles.active=dev
java -jar ./user/service/target/user-service.jar --spring.profiles.active=dev
java -jar ./service/target/service.jar --spring.profiles.active=dev
```

#### Основной сервис

Доступен по адресу: [http://localhost:8081](http://localhost:8081])

Для доступа к функционалу пользователь должен получить токен в сервисе аутентификации 

#### Сервис редактирования правил

Адрес: [http://localhost:8082](http://localhost:8082])

В системе преднастроены два правила:

- `time-rule`, для периода времени с 9 до 12 часов
- `deviceRule`, для устройства `PC`

#### Сервис аутентификации

Адрес: [http://localhost:8181](http://localhost:8181])

Преднастроенные юзеры:

* Администратор: admin/12345
* Пользователь: user/123

#### Swagger

Swagger доступен по адресам:

- auth: http://localhost:8181/swagger-ui/index.html
- editor: http://localhost:8082/swagger-ui/index.html
- jwt: http://localhost:8182/swagger-ui/index.html
- service: http://localhost:8081/swagger-ui/index.html
- user: http://localhost:8183/swagger-ui/index.html

#### Работа с системой

1. Получение токена

   Запрос:
    ```bash
    curl -X 'POST' \
      'http://localhost:8181/api/v1/auth/login' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
      "login": "user",
      "password": "123"
    }'
    ```

   Ответ:
    ```bash
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoiZXhhbXBsZSIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzIzODY1NzAyLCJleHAiOjE3MjM5NTIxMDJ9.Z7L9Y4iCXsBPRQe2cPD-0Df_mHGykqa85UiMqYXaUOs"
    }
    ```

2. Получить новую ссылку(редирект), с использованием полученного ранее токена

   Запрос:
    ```bash
   curl -X 'POST' \
     'http://localhost:8081/api/v1/redirect' \
     -H 'accept: */*' \
     -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoiZXhhbXBsZSIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzIzODY1NzAyLCJleHAiOjE3MjM5NTIxMDJ9.Z7L9Y4iCXsBPRQe2cPD-0Df_mHGykqa85UiMqYXaUOs' \
     -H 'Content-Type: application/json' \
     -d '{
     "device": "PC"
   }'    
    ```

   Ответ:
    ```bash
   {
     "link": "device-rule-link"
   }    
    ```

### Завершение работы с системой

#### Docker

Останов приложения:

```bash
 docker-compose down
```

## Проблемы 

При увеличении пользователей будет расти нагрузка на сервис и может увеличиться время отклика сервиса

## Возможные решения:

### Увеличение вычислительных мощностей

Увеличить количество инстансов приложения

Далее необходимо будет задуматься о распреелённой системе и применить горизонтальное масштабирование.

Например:

- использования облачных сервисов, K8S для инстансов каждого микросервиса
- шардирование баз

### Распределённая система

Если впоследствии функционал будет расширен может возникнуть вопрос о распределённых транзакциях.

Как вариант решения шаблон SAGA, либо использование акторной модели.
