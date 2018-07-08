FROM csfstratio/dockerbase

VOLUME /tmp

ADD @project.artifactId@-@project.version@.jar app.jar

ADD entrypoint.sh entrypoint.sh

RUN touch /data/app.jar && \
    chmod 600 /data/entrypoint.sh

ENTRYPOINT ["bash", "/data/entrypoint.sh" ]