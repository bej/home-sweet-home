#!/bin/bash

rm -r ./home-sweet-home/src/generated
swagger-codegen generate -i ./api-docs.json -o ./home-sweet-home/src/generated -l typescript-angular