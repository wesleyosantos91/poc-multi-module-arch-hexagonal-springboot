#!/bin/bash

echo "##############################"
echo "removendo as imagens docker"
echo "##############################"
sh remove-docker-imagens.sh

echo "##############################"
echo "buildando a aplicação"
echo "##############################"
sh build.sh

echo "##############################"
echo "subindos os containers docker"
echo "##############################"
cd ..
docker-compose up