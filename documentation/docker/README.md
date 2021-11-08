[How to Install Oracle XE](https://blogs.oracle.com/oraclemagazine/post/deliver-oracle-database-18c-express-edition-in-containers)
[Oracle images](https://github.com/oracle/docker-images/tree/main/OracleDatabase/SingleInstance)

```
docker run --name xedb \
-p 51521:1521 \
-p 55500:5500 \
-e ORACLE_PWD=manager \
-e ORACLE_CHARACTERSET=AL32UTF8 \
oracle/database:18.4.0-xe
```
or 

```
mkdir -p /home/myuser/oracle
mkdir -p /home/myuser/oracle/volumes
mkdir -p /home/myuser/oracle/volumes/xedb
mkdir -p /home/myuser/oracle/volumes/xedb/oradata
mkdir -p /home/myuser/oracle/volumes/xedb/scripts
mkdir -p /home/myuser/oracle/volumes/xedb/scripts/setup
mkdir -p /home/myuser/oracle/volumes/xedb/scripts/startup

sudo chmod 777 -R /home/myuser/oracle
sudo chmod 777 /home/myuser/oracle/volumes/xedb/oradata
```

```
docker run --name xedb \
-p 51521:1521 \
-p 55500:5500 \
-e ORACLE_PWD=manager \
-e ORACLE_CHARACTERSET=AL32UTF8 \
-v /home/myuser/oracle/volumes/xedb/oradata:/opt/oracle/oradata \
-v /home/myuser/oracle/volumes/xedb/scripts/setup:/opt/oracle/scripts/setup \
-v /home/myuser/oracle/volumes/xedb/startup:/opt/oracle/scripts/startup \
oracle/database:18.4.0-xe
```

```
docker-compose -f /<full path to docker samples>/docker/oraclexe.yml up
```
