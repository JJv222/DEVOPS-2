#!/bin/bash
set -e

REPO_URL="https://oauth2:${GITHUB_PAT}@github.com/JA263855"
FRONTEND_URL="$REPO_URL/frontend.git"
BACKEND_URL="$REPO_URL/backend.git"

REPO_DIR="/home/vagrant/build/repo"
ART_DIR="/home/vagrant/build/artifacts"

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
mvn -B -DskipTests clean install 

mkdir -p "$ART_DIR/backend"
cp target/*.jar "$ART_DIR/backend/app.jar"

# FRONTEND
echo "===> Building frontend"
cd "$REPO_DIR/frontend"
npm install
npm run build -- --configuration production

mkdir -p "$ART_DIR/frontend"
rm -rf "$ART_DIR/frontend/*"
cp -R dist/* "$ART_DIR/frontend/"

echo "===> Build complete."
echo "Artifacts saved in: $ART_DIR"