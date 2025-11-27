#!/bin/bash
set -e

########################################
# KONFIGURACJA DEPLOYU
########################################

# Te IP/hostname MUSISZ ustawić pod swoje maszyny:
FRONTEND_HOST="${FRONTEND_HOST}" 
BACKEND_HOST="${BACKEND_HOST}"    

SSH_USER="vagrant"

# Katalogi docelowe na maszynach front/back
FRONTEND_DEPLOY_DIR="/opt/cinema/frontend"
BACKEND_DEPLOY_DIR="/opt/cinema/backend"

# Lokalny katalog z artefaktami na Build (ten sam co w build.sh)
ART_DIR="/home/vagrant/build/deploy"

mkdir -p "${ART_DIR}"

BACKEND_JAR_LOCAL="${ART_DIR}/backend.jar"
FRONTEND_TGZ_LOCAL="${ART_DIR}/frontend.tar.gz"

FRONTEND_PORT="${FRONTEND_PORT}"   # np. 4300
BACKEND_PORT="${APP_PORT}"         # np. 8080 

########################################
# ZMIENNE NEXUSA – TAKIE SAME JAK W build.sh
########################################

NEXUS_HOST="${NEXUS_HOST}"
NEXUS_PORT="${NEXUS_PORT}"
# NEXUS_REPO, NEXUS_USER, NEXUS_PASSWORD przychodzą z env (Ansible)
NEXUS_URL="http://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/releases"

echo "===> Using Nexus at: ${NEXUS_URL}"
echo "===> Repo: ${NEXUS_REPO}"

########################################
# 1) POBIERZ ARTEFAKTY Z NEXUSA
########################################

echo
echo "===> Downloading backend.jar from Nexus..."
curl -f -L -u "${NEXUS_USER}:${NEXUS_PASSWORD}" \
  -o "${BACKEND_JAR_LOCAL}" \
  "${NEXUS_URL}/backend.jar"

echo "Saved backend.jar to ${BACKEND_JAR_LOCAL}"

echo
echo "===> Downloading frontend.tar.gz from Nexus..."
curl -f -L -u "${NEXUS_USER}:${NEXUS_PASSWORD}" \
  -o "${FRONTEND_TGZ_LOCAL}" \
  "${NEXUS_URL}/frontend.tar.gz"

echo "Saved frontend.tar.gz to ${FRONTEND_TGZ_LOCAL}"

ls -lh "${BACKEND_JAR_LOCAL}" "${FRONTEND_TGZ_LOCAL}"

########################################
# 2) DEPLOY FRONTEND
########################################

echo
echo "===> Deploy FRONTEND to ${FRONTEND_HOST}..."

ssh "${SSH_USER}@${FRONTEND_HOST}" "sudo mkdir -p '${FRONTEND_DEPLOY_DIR}' && sudo chown -R ${SSH_USER}:${SSH_USER} '${FRONTEND_DEPLOY_DIR}'"

scp "${FRONTEND_TGZ_LOCAL}" "${SSH_USER}@${FRONTEND_HOST}:/tmp/frontend.tar.gz"

ssh "${SSH_USER}@${FRONTEND_HOST}" "
  set -e
  mkdir -p '${FRONTEND_DEPLOY_DIR}'
  rm -rf '${FRONTEND_DEPLOY_DIR}'/*
  tar xzf /tmp/frontend.tar.gz -C '${FRONTEND_DEPLOY_DIR}'
"
echo "===> Start FRONTEND (static build) on ${FRONTEND_HOST}..."

ssh -T -f -n "${SSH_USER}@${FRONTEND_HOST}" "bash -lc '
  cd \"${FRONTEND_DEPLOY_DIR}/frontend/browser\" || exit 1
  nohup npx serve -s . -l ${FRONTEND_PORT} > ../frontend.log 2>&1 &
'"


echo "Frontend should be running on ${FRONTEND_HOST}, log: ${FRONTEND_DEPLOY_DIR}/frontend.log"
echo "Frontend deployed to ${FRONTEND_DEPLOY_DIR} on ${FRONTEND_HOST}"
########################################
# 3) DEPLOY BACKEND
########################################

# echo
# echo "===> Deploy BACKEND to ${BACKEND_HOST}..."

# ssh "${SSH_USER}@${BACKEND_HOST}" "sudo mkdir -p '${BACKEND_DEPLOY_DIR}' && sudo chown -R ${SSH_USER}:${SSH_USER} '${BACKEND_DEPLOY_DIR}'"

# scp "${BACKEND_JAR_LOCAL}" "${SSH_USER}@${BACKEND_HOST}:/tmp/backend.jar"

# echo "===> Start BACKEND on ${BACKEND_HOST}..."

# ssh -f -n "${SSH_USER}@${BACKEND_HOST}" "
#   cd '${BACKEND_DEPLOY_DIR}' && \
#   nohup java \
#     -Dserver.address=0.0.0.0 \
#     -Dserver.port=${BACKEND_PORT} \
#     -Dspring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME} \
#     -Dspring.datasource.username=${DB_USER} \
#     -Dspring.datasource.password=${DB_PASSWORD} \
#     -jar backend.jar > backend.log 2>&1
# "
# echo "Backend should be running on ${BACKEND_HOST}, log: ${BACKEND_DEPLOY_DIR}/backend.log"



# echo "Backend deployed to ${BACKEND_DEPLOY_DIR} on ${BACKEND_HOST}"

echo "===> DEPLOY FINISHED SUCCESSFULLY."
