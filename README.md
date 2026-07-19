# RosettaMemphis

### Postgres
1. Download and install postgres.
2. Verify if it is running in port 5432 (default port)
3. Set `usr : postgres` `pass : password1107`
4. Run the following query. Spring JPA will auto-create the tables.

```sql
CREATE DATABASE rosetta;
```

### Cassandra and Kafka installation
1. Install WSL for windows, which will subsequently install ubuntu.
2. Download and install docker from docker.com (AMD64 version.). Installer will auto-choose WSL.
3. Start the docker desktop program.
4. Put docker-compose.yml in a folder, cd to the folder and execute : `docker compose up -d`

### Cassandra
```shell
docker exec -it cassandra cqlsh
```
```sql
CREATE KEYSPACE rosetta WITH replication = {'class':'SimpleStrategy','replication_factor':1};
```
```sql
USE rosetta;
```
```sql
CREATE TABLE translation_cache(
    cache_key       text,
    translated_text text,
    PRIMARY KEY (cache_key)
);
```

### Redis (Memurai)

1. Install Memurai.
2. Edit memurai.conf in Memurai's install directory.
```text
maxmemory 100mb
maxmemory-policy allkeys-lru
```