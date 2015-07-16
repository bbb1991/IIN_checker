# Build and run java file. I love so much bash!
javac $1 && java -ea ${1%%.*}
