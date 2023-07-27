There are two application.properties files in tje project:
application.properties -> use for DEV and H2 DB;
application-production.properties -> use for PROD and PostgreSSQL DB.

For connection use defaults:

DB user = user
DB password = 12345

Для тесту з PostgreSQL базою використовувуйте application-production.properties та створіть і запусть Docker контейнер за інструкцією:

1.Сторити контейнер docker compose за допомогою файлу docker-compose:
docker-compose up -d
??? цю команду запускати у директорії з файлом docker-compose.yml наступного змісту:
-----------------------------------------------------------------------------------
version: "3.9"
services:
  postgres:
    image: postgres:13.11-bullseye
    container_name: note-db-container
    environment:
      POSTGRES_DB: "notedb"
      POSTGRES_USER: "user"
      POSTGRES_PASSWORD: "12345"
    ports:
      - "5432:5432"
-----------------------------------------------------------------------------------

2.Зупинити контейнер з БД:
docker stop note-db-container

3.Запустити контейнер з БД:
docker start note-db-container
