FROM java:8
VOLUME /tmp
copy eveng_jar eveng_jar
RUN bash -c 'touch /eveng_jar/eveng.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eveng_jar/eveng.jar"]
