# _RATATUILLE_


![Logo](./src/main/resources/static/img/logo-small.png)
## Basic functionality.

- Maintaining a user base.
- Saving, editing, tagging recipes, adding images to them.
- Combining users into groups, access to private recipes in groups, the ability to share recipes between users.

## Usage
### There are two application.properties files in tje project:
```application.properties       ```-> use as DEFAULT    for DEV, TEST  build: configuration with H2 DB; </br>
```application-production.properties ```-> use as PRODUCTION for PRODUCTION build: configuration with PostgreSQL DB

To build application with PRODUCTION profile set -D variable:

1.Add this code in the build.gradle file:

```
bootRun {
jvmArgs = ["-Dspring.profiles.active=production"]
}
```
OR

2.Run application with environment variable ```spring.profiles.active=production``` in your IDE:

### DB CONNECTION CONFIGURATION AND SET ENVIRONMENT VARIABLES:

1.Create environment variables:
```
${NOTE_DB_USER}         =>  DB user;
${NOTE_DB_PASSWORD}     =>  DB password;
${S3_ACCESS_KEY}        =>  Amazon Web Services Access Key;
${S3_SECURE_KEY}        =>  Amazon Web Services Secure Key;
```

Example for Windows, run  CMD and execute commands:
```
setx NOTE_DB_USER "user"
setx NOTE_DB_PASSWORD "12345"
setx S3_ACCESS_KEY "xxx"
setx S3_SECURE_KEY "yyy"
```
Example for Linux, run  SH and execute commands:
```
export NOTE_DB_USER='user'
export NOTE_DB_PASSWORD='12345'
export S3_ACCESS_KEY 'xxx'
export S3_SECURE_KEY 'yyy'
```
For connection use defaults:
```
NOTE_DB_USER = user
NOTE_DB_PASSWORD = 12345
```

## DOCKER CONFIGURATION:

For a test with a PostgreSQL database, use application-production.properties and create and run a Docker container according to the instructions:

1. Create a docker compose container using the docker-compose file:
   ```docker-compose up -d```

Run this command in the directory with the docker-compose.yml file with the following content:
```
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
```

2. Stop the container from the database:
   ```docker stop note-db-container```

3. Start the container from the database:
   ```docker start note-db-container```
