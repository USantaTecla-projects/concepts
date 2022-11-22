cd frontend
ng build
rm -rf ../backend/src/main/resources/static/*
cp -r dist/frontend/* ../backend/src/main/resources/static
cd ../backend
mvn install -DskipTests
sudo docker build -t concepts.jar .
echo Docker Image 'concepts.jar' Updated
