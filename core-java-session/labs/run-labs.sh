#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
SRC="$ROOT/src"
OUT="$ROOT/out"

rm -rf "$OUT"
mkdir -p "$OUT"

echo "Compiling banking labs..."
find "$SRC" -name "*.java" > "$OUT/sources.txt"
javac -encoding UTF-8 -d "$OUT" @"$OUT/sources.txt"

run_demo() {
  echo
  echo "============================================================"
  echo " $1"
  echo "============================================================"
  java -cp "$OUT" "$2"
}

run_demo "OOP" "com.banking.session.oop.DemoOOP"
run_demo "Collections & Iterators" "com.banking.session.collections.DemoCollections"
run_demo "Generics" "com.banking.session.generics.DemoGenerics"
run_demo "Capstone" "com.banking.session.capstone.BankingApp"

echo
echo "All demos finished."
