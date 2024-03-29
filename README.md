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

### Production

If you want to execute the app in production mode, you need to execute (inside `./backend`):
```bash
$ sudo docker-compose --env-file .env.prod up
```
This will setup 3 containers. The database, pgAdmin and the dockerized app. 
---

### Development

To setup the database for development you need to execute (inside `./backend`):
```bash
$ sudo docker-compose --env-file .env.dev up database pgadmin
```
This will setup 2 docker containers, one exposed on port 5432, which is the database, and the other exposed on port 80, which is pgAdmin (GUI database tool).

Once the containers are up, you can start the API in your IDE.

Then, you can start the frontend by running (inside `./frontend`)

```bash
ng serve
```
The Angular project will run on port 4200.

---

### Test

To setup the database for testing you need to execute (inside `./backend`):
```bash
$ sudo docker-compose --env-file .env.test up database pgadmin
```
This will setup 2 docker containers, one exposed on port 5432, which is the database, and the other exposed on port 80, which is pgAdmin (GUI database tool).

Then you will need to define some configuration in your IDE to override the database URL to point to the testing database. Or just change the application.properties every time you run some tests.

