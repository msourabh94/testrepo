#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
SRC="$ROOT/src"
OUT="$ROOT/out"

rm -rf "$OUT"
mkdir -p "$OUT"

echo "Compiling labs..."
find "$SRC" -name "*.java" > "$OUT/sources.txt"
javac -encoding UTF-8 -d "$OUT" @"$OUT/sources.txt"

run_demo() {
  echo
  echo "============================================================"
  echo " $1"
  echo "============================================================"
  java -cp "$OUT" "$2"
}

run_demo "OOP" "com.learnhub.session.oop.DemoOOP"
run_demo "Collections & Iterators" "com.learnhub.session.collections.DemoCollections"
run_demo "Generics" "com.learnhub.session.generics.DemoGenerics"
run_demo "Capstone" "com.learnhub.session.capstone.CatalogApp"

echo
echo "All demos finished."
