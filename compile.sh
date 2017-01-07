#!/bin/bash

find ./stairway -name '*.scala' | xargs scalac $*
