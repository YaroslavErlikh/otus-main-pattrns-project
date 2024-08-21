# Проектная работа курса OTUS Архитектура и шаблоны проектирования

[![codecov](https://codecov.io/github/skubatko/2024-03-otus-main-patterns-Kubatko--project/graph/badge.svg?token=R1I2WISJO2)](https://codecov.io/github/skubatko/2024-03-otus-main-patterns-Kubatko--project)

## Описание

Умные ссылки.

Пользователь переходит по ссылке. В зависимости от набора правил, которые могут включать интервалы времени,
местоположение пользователя, устройство, браузер и др. система выполняет редирект на урл, соответствующий сработавшему
правилу.

## Архитектура

![architecture.jpg](doc%2Farchitecture.jpg)

## Работа с системой

### Предварительные требования

Необходимо иметь установленными:

- Java версии 17
- maven версии 3.8.1
- Docker

### Сборка системы

Сборка модулей системы осуществляется командой:

```bash
mvn clean install
```

### Запуск системы

#### Docker

Запуск в Docker осуществляется командой:

```bash
 docker-compose up -d --build
```

Первоначальный старт системы может занять длительное время. Это связано с необходимостью подготовки образов Docker.

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

### Работа с системой

#### Основной сервис для работы пользователей

Доступен по адресу: [http://localhost:8081](http://localhost:8081])

Для работы с системой пользователь должен получить JWT-токен в сервисе аутентификации и использовать его для получения
доступа к функционалу системы.

#### Сервис редактирования правил

Доступен по адресу: [http://localhost:8082](http://localhost:8082])

В системе преднастроены два правила:

- `time-rule`, для периода времени с 9 до 12 часов
- `deviceRule`, для устройства `PC`

#### Сервис аутентификации

Доступен по адресу: [http://localhost:8181](http://localhost:8181])

В системе преднастроены два пользователя:

* Администратор: admin/12345
* Пользователь: user/123

#### Swagger

Для работы с системой можно использовать swagger по следующим адресам:

- auth: http://localhost:8181/swagger-ui/index.html
- editor: http://localhost:8082/swagger-ui/index.html
- jwt: http://localhost:8182/swagger-ui/index.html
- service: http://localhost:8081/swagger-ui/index.html
- user: http://localhost:8183/swagger-ui/index.html

#### Пример работы с системой

1. Получить токен в модуле auth

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

2. Получить редирект ссылку, с применением токена из п.1

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

### Окончание работы с системой

#### Docker

Останов работы осуществляется командой:

```bash
 docker-compose down
```

#### Standalone

Останов работы осуществляется остановом работы модулей системы в произвольном порядке.

## Проблемы сложности

С ростом числа потребителей сервиса будет расти нагрузка на сервис и может возникнуть проблема задержки отклика сервиса,
что снизит качество обслуживания клиентов и может привести к падению дохода владельцев сервиса.

Чтобы предупредить данную проблему можно предпринять ряд мер.

### БД

Для хранения правил редиректа подходит нереляционная БД, так как DSL правил подвержен постоянному изменению.
Документоориентированная MongoDB как раз хорошо подходит для данной цели.

Для хранения пользователей используем реляционную БД, так как набор атрибутов пользователя заранее известен и не
потребует частых изменений.

### Увеличение вычислительных мощностей

По мере роста проекта можно до определённого момента расширять имеющиеся вычислительные ресурсы. То есть применять так
называемое Вертикальное масштабирование.

По достижении определённого показателя нагрузки необходимо задуматься о распреелённой системе и применить горизонтальное
масштабирование.

Здесь можно отметить следующие варинаты:

- перевод сервиса в облако и применение K8S для разворачивания нескольких инстансов каждого из микросервисов
- шардирование БД

### Распределённая система

Со временем функционал системы может быть расширен и встанет вопрос о транзакционности в распределённой системе.

Здесь можно рекомендовать обратить внимание на архитектурный шаблон SAGA.

А также посмотреть в сторону применения акторной модели.
