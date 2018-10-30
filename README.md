# SHACL Service

## About
This simple package takes the TopQuadrant SHACL library (https://github.com/TopQuadrant/shacl)  
 and wraps it in a simple RESTful service using Javalin (https://javalin.io/).   

## Alternatives
Another SHACL library was brought to my attention after this work which may be of interest to 
those wanting to live in Python land vs Java land.  PySHACL can be found at
https://github.com/RDFLib/pySHACL 

## Compiling

A POM file is included so you should be able to compile with

```
mvn clean compile assembly:single
```


## Runing

At the command line you can use 

```
java -jar shaclservice-1.0-SNAPSHOT-jar-with-dependencies.jar
```


## Testing

For testing you can use curl or Postman or httpie (https://httpie.org/) which is what I use.  
Example test calls below:

```
http -f POST localhost:7000  files@~/Semantic/SHACL/datagraph.ttl shape@~/Semantic/SHACL/shape.ttl 
http -f POST localhost:7000  files@~/Semantic/SHACL/datagraph.ttl shape@~/Semantic/SHACL/recomendShape.ttl 
http -f POST localhost:7000  files@~/Project418/garden/shacl/ocddataset.ttl  shape@~/Project418/garden/shacl/recomendShape.ttl 
http -f POST localhost:7000  files@~/Project418/garden/shacl/ocddataset.ttl  shape@~/Project418/garden/shacl/requiredShape.ttl 
```

## Docker

A Docker container can also be built using the Dockerfile in this repo.  

```
docker build -t fils/shaclservice:0.1 .
```
