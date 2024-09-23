.PHONY: up create/aws/resources

up:
	docker-compose up -d

create/aws/resources:
	./init_localstack/10_create_lambda_function.sh
	./init_localstack/20_create_sns_topic.sh
