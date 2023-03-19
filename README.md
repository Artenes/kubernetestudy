# ANote

Sample app to practice kubernetes deployment from this [learnk8s tutorial](https://learnk8s.io/spring-boot-kubernetes-guide#creating-a-kubernetes-cluster-on-aws).

# Running the app

First:

````
.\gradlew clean assemble
````

Then:

````
docker-compose --profile dev up -d
````

You can checkout the app at http://localhost:8080.

# Kubernetes

The whole kubernetes setup is defined in the ``kube`` folder. You just need to deploy it to
a kubernetes cluster to test it.