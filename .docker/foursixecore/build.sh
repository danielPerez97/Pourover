#!/bin/bash

set -e

echo "Building foursixcore-base image..."
docker build -f Dockerfile -t foursixcore-base ../..

echo "Build complete."
