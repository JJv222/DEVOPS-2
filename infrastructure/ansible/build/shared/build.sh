#!/bin/bash
set -e

REPO_URL="https://oauth2:${GITHUB_PAT}@github.com/JA263855"
FRONTEND_URL="$REPO_URL/frontend.git"
BACKEND_URL="$REPO_URL/backend.git"

REPO_DIR="/home/vagrant/build/repo"
ART_DIR="/home/vagrant/build/artifacts"

NEXUS_HOST="${NEXUS_HOST}"
NEXUS_PORT="${NEXUS_PORT}"
NEXUS_URL="http://${NEXUS_HOST}:${NEXUS_PORT}/repository/${NEXUS_REPO}/releases"

echo "===> Cleaning repo folder"
rm -rf "$REPO_DIR"
mkdir -p "$REPO_DIR"

echo "===> Cloning backend"
git clone "$BACKEND_URL" "$REPO_DIR/backend"

echo "===> Cloning frontend"
git clone "$FRONTEND_URL" "$REPO_DIR/frontend"

# BACKEND
echo "===> Building backend"
cd "$REPO_DIR/backend"
mvn -B -DskipTests clean package 

mkdir -p "$ART_DIR/backend"
cp target/*.jar "$ART_DIR/backend/backend.jar"

# ===== UPLOAD DO NEXUSA =====
echo "CMD: curl -L -u '${NEXUS_USER}:${NEXUS_PASSWORD}' --upload-file '${ART_DIR}/backend/backend.jar' '${NEXUS_URL}/backend.jar'"
echo "===> Uploading backend.jar to Nexus Repository"
curl -u "${NEXUS_USER}:${NEXUS_PASSWORD}" \
  --upload-file "$ART_DIR/backend/backend.jar" \
  "${NEXUS_URL}/backend.jar"

echo "===> Uploaded backend.jar to Nexus Repository!!!"

# FRONTEND
echo "===> Building frontend"
cd "$REPO_DIR/frontend"
npm install
npm run build -- --configuration production

mkdir -p "$ART_DIR/frontend"
rm -rf "$ART_DIR/frontend/*"
cp -R dist/* "$ART_DIR/frontend/"

echo "===> Packaging frontend as tar.gz"
cd "$ART_DIR/frontend"
tar czf "$ART_DIR/frontend/frontend.tar.gz" .

echo "===> Build complete."
echo "Artifacts saved in: $ART_DIR"

# ===== UPLOAD DO NEXUSA =====
echo "===> Uploading frontend.tar.gz to Nexus Repository"
curl -u "${NEXUS_USER}:${NEXUS_PASSWORD}" \
  --upload-file "$ART_DIR/frontend/frontend.tar.gz" \
  "${NEXUS_URL}/frontend.tar.gz"

echo "===> Uploaded frontend.tar.gz to Nexus Repository!!!"