# RosettaMemphis

### Overview
**Rosetta is an AI powered spring web application which can asynchronously process massive amounts of translations.**

### Features
1. Can use local or cloud model based on plan.
2. Models can be easily upgraded as required.
3. Translations are produced in a kafka message queue and retrieved asynchronously.
4. Free plan gets one kafka consumer thread. Paid gets two for priority processing.
5. Translations are cached in-memory and in persistent storage for sub millisecond processing.
6. Provides API key based authentication for seamless integration.

### Technology Stack
1. Spring Boot 4.1.0
2. Spring AI (with Ollama and Gemini as model providers)
3. Spring Data JPA / Hibernate and PostgreSQL
4. Spring Kafka
5. Spring Data Redis
6. Spring Data Cassandra

## Setup

### Postgres
1. Download and install postgres.
2. Verify if it is running in port 5432 (default port)
3. Set `usr : postgres` `pass : password1107`
4. Run the following query before app server startup. `CREATE DATABASE rosetta;`
5. Spring JPA will auto-create the tables : `users` `api_keys` `translation_history`

### Cassandra and Kafka installation
1. Install WSL for windows, which will subsequently install ubuntu.
2. Download and install docker from docker.com (AMD64 version.). Installer will auto-choose WSL.
3. Start the docker desktop program.
4. Put docker-compose.yml in a folder, cd to the folder and execute : `docker compose up -d`
5. Repeat 3 and 4 for cold starts.

### Cassandra
1. Cassandra usually takes a minute to start-up.
2. The keyspace `rosetta` and the table `translation_cache` is auto-created during server startup.

```shell
docker exec -it cassandra cqlsh
```

### Redis (Memurai)
1. Install Memurai.
2. Edit memurai.conf in Memurai's install directory.
```text
maxmemory 100mb
maxmemory-policy allkeys-lru
```

### Gemini
1. Add your gemini API key to `application.properties` for the key `spring.ai.google.genai.api-key`

### Ollama
1. Install Ollama.
2. Go to Ollama's website and select a suitable translation based model.
3. Run the command provided in Ollama's website to pull the model locally.
4. Ensure that ollama is running before app server startup.
5. Add the exact name of the model to the key `spring.ai.ollama.chat.options.model` in `application.properties`

## Request CURL

## Users
1. Users APIs are authenticated via an admin API key. Can be retrieved/modified in `application.properties` for the key `app.admin.apikey`.
2. Plan 0 - free, 1 - paid.
3. Adding a user will provide five API keys in the response.

### Add User
```bash
curl --request POST \
  --url http://localhost:1107/api/v1/users \
  --header 'API-KEY: bdca33df-2612-48a5-bead-218836f0c60a' \
  --header 'content-type: application/json' \
  --data '{
  "plan":1
}'
```

### Update User
```bash
curl --request PATCH \
  --url http://localhost:1107/api/v1/users/4 \
  --header 'API-KEY: bdca33df-2612-48a5-bead-218836f0c60a' \
  --header 'content-type: application/json' \
  --data '{
  "plan":0
}'
```

### Delete User
```bash
curl --request DELETE \
  --url http://localhost:1107/api/v1/users/6 \
  --header 'API-KEY: bdca33df-2612-48a5-bead-218836f0c60a' \
  --header 'content-type: application/json' \
  --data '{
  "plan":0
}'
```

## Translation
Use the API key of the respective user.

### Translate
```bash
curl --request POST \
  --url http://localhost:1107/api/v1/translate \
  --header 'api-key: b5e968f6-1d5b-4232-95ab-e2ebf89de860' \
  --header 'content-type: application/json' \
  --data '{
  "source_language":"en",
  "translation_language":"fr",
  "source_text":"Hey listen up!"
}'
```

### Get Translation
```bash
curl --request GET \
  --url http://localhost:1107/api/v1/translate/1283 \
  --header 'api-key: b5e968f6-1d5b-4232-95ab-e2ebf89de860'
```

### Get Translations - Bulk
```bash
curl --request GET \
  --url 'http://localhost:1107/api/v1/translate?page_number=0&page_size=50&is_ascending=false' \
  --header 'api-key: b5e968f6-1d5b-4232-95ab-e2ebf89de860'
```