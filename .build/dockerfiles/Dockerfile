#
# STAGE 1 - Dev
#
# Dev image used for local development
#
FROM zcscompany/java:17-dev AS dev

ARG FIX_UID
ARG FIX_GID
ARG DOCKER_USER=bob
ARG DOCKER_GROUP=bob

USER 0

#RUN apt-get update && \
#    apt-get install --no-install-recommends -y \
#        YOUR_PKGS_HERE && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*

RUN /fix-perm.sh

# Run as normal user
USER ${DOCKER_USER}

#
# Stage 2 - Package
#
# This stage prepares a "package" image used to generate application package.
#
FROM dev AS package

COPY --chown=${DOCKER_USER}:${DOCKER_GROUP} app/ .
RUN mvn --no-transfer-progress clean package

#
# Stage 3 - Dist
#
# This stage prepares a dist image used to run the application.
#
FROM zcscompany/java:17-dist AS dist

ARG DOCKER_USER=bob
ARG DOCKER_GROUP=bob

#USER 0

#RUN apt-get update && \
#    apt-get install --no-install-recommends -y \
#        YOUR_PKGS_HERE && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/*

# Run as normal user
#USER ${DOCKER_USER}

COPY --from=package --chown=${DOCKER_USER}:${DOCKER_GROUP} /app/target/app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
