#!/bin/bash
java -Dserver.port=$PORT $JAVA_OPTS -jar target/portfolio-0.0.1-SNAPSHOT.war
