FROM csfstratio/dockerbase

VOLUME /tmp

ADD target/micro-ci-test-1.0.1.jar app.jar

ADD entrypoint.sh entrypoint.sh

RUN touch /data/app.jar && \
    chmod 600 /data/entrypoint.sh

ENTRYPOINT ["bash", "/data/entrypoint.sh" ]