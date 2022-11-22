# Concepts
Tool to auto generate exams from a knowledge base.

## Scripts

To setup the database for development you need to execute:
```bash
$ sudo docker-compose --env-file .env.dev up database pgadmin
```
This will setup 2 docker containers, one exposed on port 5432, which is the database, and the other exposed on port 80, which is pgAdmin (GUI database tool).

Once the containers are up, you can start your application in your IDE.

In development mode the app is exposed on port 8544

---

If you want to execute the app in production mode, you need to execute:
```bash
$ sudo docker-compose --env-file .env.prod up
```
This will setup 3 containers. The database, pgAdmin and the dockerized backend. 

In production mode the app is exposed on port 8543.

