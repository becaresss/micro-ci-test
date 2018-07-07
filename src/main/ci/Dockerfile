FROM csfstratio/dockerbase

VOLUME /tmp

ADD target/${project.artifactId:micro-ci-test}-${project.version:1.0.2-SNAPSHOT}.jar app.jar

ADD entrypoint.sh entrypoint.sh

RUN touch /data/app.jar && \
    chmod 600 /data/entrypoint.sh

ENTRYPOINT ["bash", "/data/entrypoint.sh" ]