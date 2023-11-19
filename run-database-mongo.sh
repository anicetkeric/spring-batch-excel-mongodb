#!/usr/bin/env bash

# Exécuter MongoDB Container
docker run -d -p 27017:27017 \
    --name test-mongo \
    -v mongo-data:/data/db \
    -e MONGODB_INITDB_ROOT_USERNAME=admin \
    -e MONGODB_INITDB_ROOT_PASSWORD=pass \
    -e MONGODB_INITDB_DATABASE=spring_batch_excel_mongodb_db \
    mongo:latest


# Exécuter MongoDB Express Container
docker run --link test-mongo:mongo \
    -p 8222:8222 \
    -e ME_CONFIG_MONGODB_URL="mongodb://mongo:27017" \
    -e PORT=8222 \
    mongo-express

#  "Utilisateur MongoDB Express: admin"
#  "Mot de passe MongoDB Express: pass"


