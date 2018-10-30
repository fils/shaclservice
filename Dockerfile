FROM  anapsix/alpine-java:9
WORKDIR /
ADD target/shaclservice-1.0-SNAPSHOT-jar-with-dependencies.jar shaclservice-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 7000
CMD java -jar shaclservice-1.0-SNAPSHOT-jar-with-dependencies.jar