FROM csfstratio/dockerbase

VOLUME /tmp

ADD target/micro-ci-test-1.0.2 micro-ci-test.jar

ADD entrypoint.sh entrypoint.sh

RUN sh -c 'touch micro-ci-test.jar' && chmod +x entrypoint.sh

ENTRYPOINT ["bash", "entrypoint.sh" ]
