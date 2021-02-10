#!/bin/bash

./gradlew asciidoctor
export VERSION=`./gradlew -q version`
export BINDIR="build/libs"
export BINFILE="Sonofy-${VERSION}"
export DOCDIR="app/build/docs/asciidoc"
export DOCFILE="sonofy"
mv "${DOCDIR}/${DOCFILE}.pdf" "${DOCDIR}/${DOCFILE}-${VERSION}.pdf"
export BINARY="${BINDIR}/${BINFILE}.jar"
export DOCUMENTATION="${DOCDIR}/${DOCFILE}-${VERSION}.pdf"
echo binary is ${BINARY}
echo documentation is ${DOCUMENTATION}
export TRAVIS_TAG=v${VERSION}
echo tag ${TRAVIS_TAG}
