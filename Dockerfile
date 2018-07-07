FROM csfstratio/dockerbase

VOLUME /tmp

ADD target/${project.artifactId}-${project.version} ${project.artifactId}.jar

ADD entrypoint.sh entrypoint.sh

RUN sh -c 'touch ${project.artifactId}.jar' && chmod +x entrypoint.sh

ENTRYPOINT ["bash", "entrypoint.sh" ]
