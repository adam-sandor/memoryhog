#!/usr/bin/env bash

#works - on the edge with memory
#docker run -ti --rm --name pinky \
#    -p 9999:9999 \
#    -e PINKY_CONSUME_MB=300 \
#    -e PINKY_RELEASE_AFTER_SEC=30 \
#    -e PINKY_CONSUME_RATE=5 \
#    -e MAX_RAM_FRACTION=1 \
#    -m 400m \
#    adamsandor83/memoryhog

docker run -ti --rm --name pinky \
    -p 9999:9999 \
    -e PINKY_CONSUME_MB=3500 \
    -e PINKY_RELEASE_AFTER_SEC=30 \
    -e PINKY_CONSUME_RATE=30 \
    -e MAX_RAM_FRACTION=1 \
    -m 3000m \
    adamsandor83/memoryhog