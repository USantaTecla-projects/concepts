# Concepts
Tool to auto generate exams from a knowledge base.

## Update Docker Image

The app run under docker in production, so when a change is made, you'll probably want to update the image to apply the changes. To achieve this:

1. If you have done changes on the frontend (if you just made changes on the API, go to step 3), you need to go to the root folder of the angular project `./frontend` and then execute the following command `$ ng build`. This will generate a folder called `./frontend/dist/frontend`.
2. Once the frontend production code has been generated, you will need to copy the content inside `./frontend/dist/frontend` to `./backend/src/main/resources/static`. Probably you will want to first clear the `static` folder and then copy the frontend code.
3. Now you need to build the new docker image. First remove the content inside `./backend/target`, then, go to the root folder of the backend project (`./backend`) and run `mvn install` to generate the JAR file. Running `mvn install` will also execute the tests, if you want to generate the JAR without running them, run `mvn install -DskipTests` instead of `mvn install`. 
4. Finally, you can run the app in production mode as explained down. 

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

