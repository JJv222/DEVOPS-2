#!/bin/bash
set -euo pipefail

########################################
# KONFIGURACJA
########################################

# Hosty (dostarczone z Ansible jako zmienne środowiskowe)
FRONTEND_HOST="${FRONTEND_HOST:?FRONTEND_HOST not set}"
BACKEND_HOST="${BACKEND_HOST:?BACKEND_HOST not set}"

SSH_USER="${SSH_USER:-vagrant}"

FRONTEND_DEPLOY_DIR="/opt/cinema/frontend"
BACKEND_DEPLOY_DIR="/opt/cinema/backend"

# Porty – raczej używane w systemd / env, ale zostawiam dla spójności
FRONTEND_PORT="${FRONTEND_PORT:-4300}"
BACKEND_PORT="${APP_PORT:-8080}"

########################################
# NEXUS
########################################

NEXUS_HOST="${NEXUS_HOST:?NEXUS_HOST not set}"
NEXUS_PORT="${NEXUS_PORT:?NEXUS_PORT not set}"
NEXUS_REPO="${NEXUS_REPO:?NEXUS_REPO not set}"
NEXUS_USER="${NEXUS_USER:?NEXUS_USER not set}"
NEXUS_PASSWORD="${NEXUS_PASSWORD:?NEXUS_PASSWORD not set}"

NEXUS_URL="http://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/releases"

echo "===> Używam Nexus URL: ${NEXUS_URL}"
echo

########################################
# 1) DEPLOY FRONTEND (na maszynie FRONTEND_HOST)
########################################

echo "===> Deploy FRONTEND na ${FRONTEND_HOST}..."

ssh "${SSH_USER}@${FRONTEND_HOST}" bash -s <<EOF
set -e

FRONTEND_DEPLOY_DIR="${FRONTEND_DEPLOY_DIR}"
NEXUS_URL="${NEXUS_URL}"
NEXUS_USER="${NEXUS_USER}"
NEXUS_PASSWORD="${NEXUS_PASSWORD}"

echo "  [FRONTEND] Tworzę katalog: \$FRONTEND_DEPLOY_DIR"
sudo mkdir -p "\$FRONTEND_DEPLOY_DIR"
sudo chown -R ${SSH_USER}:${SSH_USER} "\$FRONTEND_DEPLOY_DIR"

echo "  [FRONTEND] Pobieram frontend.tar.gz z Nexusa..."
curl -fSL -u "\$NEXUS_USER:\$NEXUS_PASSWORD" \
  -o /tmp/frontend.tar.gz \
  "\$NEXUS_URL/frontend.tar.gz"

echo "  [FRONTEND] Czyszczę katalog docelowy i rozpakowuję..."
rm -rf "\$FRONTEND_DEPLOY_DIR"/*
tar -xzf /tmp/frontend.tar.gz -C "\$FRONTEND_DEPLOY_DIR"
rm -f /tmp/frontend.tar.gz

echo "  [FRONTEND] Reload + restart systemd..."
sudo systemctl daemon-reload
sudo systemctl enable frontend.service
sudo systemctl restart frontend.service || (sudo journalctl -u frontend.service -n 50 --no-pager; exit 1)

echo "  [FRONTEND] DONE"
EOF

echo "Frontend zdeployowany na ${FRONTEND_HOST}"
echo "Sprawdź: journalctl -u frontend.service -f"
echo

########################################
# 2) DEPLOY BACKEND (na maszynie BACKEND_HOST)
########################################

echo "===> Deploy BACKEND na ${BACKEND_HOST}..."

ssh "${SSH_USER}@${BACKEND_HOST}" bash -s <<EOF
set -e

BACKEND_DEPLOY_DIR="${BACKEND_DEPLOY_DIR}"
NEXUS_URL="${NEXUS_URL}"
NEXUS_USER="${NEXUS_USER}"
NEXUS_PASSWORD="${NEXUS_PASSWORD}"

echo "  [BACKEND] Tworzę katalog: \$BACKEND_DEPLOY_DIR"
sudo mkdir -p "\$BACKEND_DEPLOY_DIR"
sudo chown -R ${SSH_USER}:${SSH_USER} "\$BACKEND_DEPLOY_DIR"

echo "  [BACKEND] Pobieram backend.jar z Nexusa..."
curl -fSL -u "\$NEXUS_USER:\$NEXUS_PASSWORD" \
  -o /tmp/backend.jar \
  "\$NEXUS_URL/backend.jar"

echo "  [BACKEND] Podmieniam JAR..."
mv /tmp/backend.jar "\$BACKEND_DEPLOY_DIR/backend.jar"

echo "  [BACKEND] Reload + restart systemd..."
sudo systemctl daemon-reload
sudo systemctl enable cinema-backend.service
sudo systemctl restart cinema-backend.service || (sudo journalctl -u cinema-backend.service -n 50 --no-pager; exit 1)

echo "  [BACKEND] DONE"
EOF

echo "Backend zdeployowany na ${BACKEND_HOST}"
echo "Sprawdź: journalctl -u cinema-backend.service -f"
echo
echo "===> DEPLOY FINISHED SUCCESSFULLY."
