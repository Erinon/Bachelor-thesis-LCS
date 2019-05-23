#! /bin/bash

echo "Compiling..."

# compile project
javac -d target/classes -sourcepath src/main/java src/main/java/hr/fer/zemris/bachelor/Application.java

echo "Done."

