#!/bin/bash
cd ..
./mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=poc-multi-module-arch-hexagonal-springboot \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=c07525e8ab19140a21fa7a0125cc22d501bd340d