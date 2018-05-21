FROM qa.stratio.com/stratio/java-microservice-dockerbase:0.2.0

VOLUME /tmp

ADD target/micro-ci-test-1.0.0-SNAPSHOT.jar micro-ci-test.jar

ADD entrypoint.sh entrypoint.sh

RUN sh -c 'touch micro-ci-test.jar' && chmod +x entrypoint.sh

ENTRYPOINT ["bash", "entrypoint.sh" ]
