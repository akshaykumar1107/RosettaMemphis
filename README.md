# RosettaMemphis

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