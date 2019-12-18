#!/usr/bin/env bash
clang -c -Wall -o zf_log.o -I ./ ./zf_log.c
llvm-ar rc ../cinterop/zf_log_linuxX64.a zf_log.o
