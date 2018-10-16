#!/bin/bash

find . -name '*.scala' | xargs scaladoc -d doc/
