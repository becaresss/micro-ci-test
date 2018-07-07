FROM csfstratio/dockerbase

VOLUME /tmp

ADD target/micro-ci-test-${v}.jar micro-ci-test.jar

ADD entrypoint.sh entrypoint.sh

RUN sh -c 'touch micro-ci-test.jar' && chmod +x entrypoint.sh

ENTRYPOINT ["bash", "entrypoint.sh" ]
