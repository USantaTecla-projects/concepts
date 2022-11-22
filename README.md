# Concepts
Tool to auto generate exams from a knowledge base.

## Update Docker Image

The app run under docker in production, so when a change is made, you'll probably want to update the image to apply the changes.

For this, you can run the script that is in the root folder of the project (`./concepts`) called `update_docker_image.sh`. 

Remember to set 777 permission to allow the execution, also you'll need to have `node` and `npm` installed, you'll need to execute `npm install` in the `./frontend` folder and you need to have installed Angular CLI. 

If you prefer do it manually, here are the steps that the script follows:

1. If you have done changes on the frontend (if you just made changes on the API, go to step 3), you need to go to the root folder of the angular project `./frontend` and then execute the following command `$ ng build`. This will generate a folder called `./frontend/dist/frontend`.
2. Once the frontend production code has been generated, you will need to copy the content inside `./frontend/dist/frontend` to `./backend/src/main/resources/static`. Probably you will want to first clear the `static` folder and then copy the frontend code.
3. Now you need to build the new docker image. First remove the content inside `./backend/target`, then, go to the root folder of the backend project (`./backend`) and run `mvn install` to generate the JAR file. Running `mvn install` will also execute the tests, if you want to generate the JAR without running them, run `mvn install -DskipTests` instead of `mvn install`. 
4. Finally, you can run the app in production mode as explained down. 

## Enviroments

To setup the database for development you need to execute (inside `./backend`):
```bash
$ sudo docker-compose --env-file .env.dev up database pgadmin
```
This will setup 2 docker containers, one exposed on port 5432, which is the database, and the other exposed on port 80, which is pgAdmin (GUI database tool).

Once the containers are up, you can start the API in your IDE.

In development mode the API is exposed on port 8544.

Then, you can start the frontend by running (inside `./frontend`)

```bash
ng serve
```

---

If you want to execute the app in production mode, you need to execute:
```bash
$ sudo docker-compose --env-file .env.prod up
```
This will setup 3 containers. The database, pgAdmin and the dockerized backend. 

In production mode the app is exposed on port 8543.

---

The app doesn't have a Test enviroment, this is made on purpose to enforce the tests to work over different kind of scenarios and to adapt the code as the errors and bugs starts to appear.

If in future developments this decission changes, then you will need to create another `.env` file called `.env.test`, where you specify the database name for the tests, and also you will need to define an env variable in `application.properties`.

