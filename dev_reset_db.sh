#!/bin/bash

CONTAINER="cockroachdb-dev"
DB_NAME="management_system"

if docker exec -i "$CONTAINER" \
    ./cockroach sql --insecure --host=localhost:26257 -e "DROP DATABASE IF EXISTS $DB_NAME CASCADE; CREATE DATABASE $DB_NAME;"; then
    echo "Database '$DB_NAME' successfully recreated."
else
    echo "Failed to reset database '$DB_NAME'." >&2
    exit 1
fi