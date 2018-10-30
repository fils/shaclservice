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

```bash
mvn clean compile assembly:single
```

## Running

At the command line you can use 

```bash
java -jar shaclservice-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Testing

For testing you can use curl or Postman or httpie (https://httpie.org/) which is what I use.  
Example test calls below:

```bash
http -f POST localhost:7000  datagraph@./testgraphs/ocddataset.ttl  shapegraph@./testgraphs/dataSetRequiredShape.ttl dataref="test1" shaperef="test2"
```
The "dataref" and "shaperef" parameters pass along the identifying string for the resources. These can be used to 
record what data and shape graphs were used.  These can be simple identifying strings or URIs.  They are simply recorded 
in the output graph as literals. 

These parameters are not required and you can simply use:

```bash
http -f POST localhost:7000  datagraph@./testgraphs/ocddataset.ttl  shapegraph@./testgraphs/dataSetRequiredShape.ttl
```

which will only test the graphs and provide a standard SHACL evaluation graph in return.


## Docker

A Docker container can also be built using the Dockerfile in this repo.  

```bash
docker build -t fils/shaclservice:0.1 .
```
