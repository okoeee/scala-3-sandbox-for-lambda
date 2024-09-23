#!/bin/bash

# Create a topic
aws --endpoint-url http://localhost:54566 sns create-topic \
  --region us-east-1 \
  --name testTopic \
  --profile localstack

# Subscribe to the topic
aws --endpoint-url http://localhost:54566 sns subscribe \
  --region us-east-1 \
  --topic-arn arn:aws:sns:us-east-1:000000000000:testTopic \
  --protocol lambda \
  --notification-endpoint arn:aws:lambda:us-east-1:000000000000:function:testFunction \
  --profile localstack
