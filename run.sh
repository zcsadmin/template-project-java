#!/bin/bash 

# Default values
EXEC=1
TESTS=0
VERBOSE=0
DEBUG=0
PREDICT=0
DISTIMAGE=0
CLEAR=0
BACKGROUND=""

set -e

show_help() {

    echo "Usage: $0 [-x] [-b] [-d] [-s] [-t] [-i] [-c] [-v] [-h]"
    echo "  -x: Execute the application (default if no command line option specified)"
    echo "  -b: Run in background"
    echo "  -d: Run application in debug mode"
    echo "  -s: Run a shell instead of running the application"
    echo "  -t: Run application tests"
    echo "  -i: Build dist image"
    echo "  -c: Clear docker environment"
    echo "  -v: Verbose output"
    echo "  -h: Show this help"
}

while getopts xp:bdsticvh flag
do
    case "${flag}" in
        x) EXEC=1;;
        p) PREDICT=1 PREDICT_ARG=${OPTARG};;
        b) BACKGROUND=" -d ";;
        d) DEBUG=1;;
        s) EXEC=0;;
        t) TESTS=1;;
        i) DISTIMAGE=1;;
        c) CLEAR=1;;
        v) VERBOSE=1;;
        h) show_help; exit 0;;
    esac
done

if [ $VERBOSE -eq 1 ]; then
    set -x
fi

if [ $DISTIMAGE -eq 1 ]; then
    # Build dist image
    docker build --pull -f .build/dockerfiles/Dockerfile -t dist .
    exit 0
fi

if [ $CLEAR -eq 1 ]; then
    # Clear docker environment
    docker compose stop
    exit 0
fi


# Build docker containers
docker compose build --pull --build-arg FIX_UID="$(id -u)" --build-arg FIX_GID="$(id -g)"

# Start docker environment
docker compose up -d

docker restart dnsdock || true

if [ $TESTS -eq 1 ]; then
    # Run tests
    docker compose exec app /bin/bash -c "mvn clean test"
    exit 0
fi

if [ $DEBUG -eq 1 ]; then
    # Run application in debug mode
    docker compose exec app /bin/bash -c "MAVEN_OPTS=\"\${DEBUG_OPTS}\" mvn clean compile exec:java"
    exit 0
fi

if [ $EXEC -eq 1 ]; then
    # Run application
    docker compose exec ${BACKGROUND} app /bin/bash -c "mvn clean compile exec:java"
else
    # Log into container
    docker compose exec app /bin/bash
fi

