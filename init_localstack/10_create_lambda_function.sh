#!/bin/bash

aws --endpoint-url http://localhost:54566 lambda create-function \
  --region us-east-1 \
  --function-name testFunction \
  --runtime java11 \
  --zip-file fileb://target/scala-3.5.1/scala-3-sandbox-for-lambda-assembly-0.1.0-SNAPSHOT.jar \
  --handler example.Main::hello \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --profile localstack
