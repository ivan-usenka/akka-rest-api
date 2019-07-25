#
# Scala and sbt Dockerfile
#
# https://github.com/hseeberger/scala-sbt
#

# Pull base image
FROM ubuntu:bionic
EXPOSE 9090

RUN groupadd -g 999 app && \
  useradd -r -u 999 -g app app

# Disable installing doc/man/locale files
RUN echo "\
path-exclude=/usr/share/doc/*\n\
path-exclude=/usr/share/man/*\n\
path-exclude=/usr/share/locale/*\n\
" > /etc/dpkg/dpkg.cfg.d/apt-no-docs

RUN mkdir -p /app && \
  chown -R app:app /app && \
  rm /bin/sh && \
  ln -sv /bin/bash /bin/sh

RUN apt-get -yqq update && \
  apt-get install -yqq default-jre && \
  apt-get clean && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /tmp/*

# Move code to container
WORKDIR /app
COPY target/scala*/*.jar /app/app.jar
ENV SCALA_ENV dev

CMD ["/usr/bin/java", "-Xmx12g", "-XX:+UseContainerSupport", "-jar", "app.jar"]

USER app