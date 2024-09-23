
# Usage 

## aws credentialsの設定
AWSのコンソールからIAMユーザーを作成し、SNS Publishの権限を付与する。
次にアクセスキーとシークレットキーを取得し、`~/.aws/credentials`に貼り付ける。

```
[sns-publisher]
aws_access_key_id = foo
aws_secret_access_key = foo
```

## `LocalStack`の起動
```
docker-compose up -d
```

## Lmabda

### Lambda関数の作成と`.jar`の作成
```shell
sbt assembly
```

### Lambda関数のデプロイ
roleは任意に設定が可能で、ロール自体の作成は必要ない
```shell
aws --endpoint-url http://localhost:54566 lambda create-function \
  --region us-east-1 \
  --function-name testFunction \
  --runtime java11 \
  --zip-file fileb://target/scala-3.5.1/scala-3-sandbox-for-lambda-assembly-0.1.0-SNAPSHOT.jar \
  --handler example.Main::hello \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --profile localstack
```

Lambda関数の更新をしたい場合は、以下のコマンドを実行する
```shell
aws --endpoint-url http://localhost:54566 lambda update-function-code \
  --region us-east-1 \
  --function-name testFunction \
  --zip-file fileb://target/scala-3.5.1/scala-3-sandbox-for-lambda-assembly-0.1.0-SNAPSHOT.jar \
  --profile localstack
```

handlerの変更をしたい場合は、以下のコマンドを実行する
```shell
aws --endpoint-url http://localhost:54566 lambda update-function-configuration \
  --region us-east-1 \
  --function-name testFunction \
  --handler example.Main::hello \
  --profile localstack
```

### Lambda関数の実行
```shell
aws --endpoint-url http://localhost:54566 lambda invoke \
  --region us-east-1 \
  --function-name testFunction \
  --payload $(echo '{"key1": "value1"}' | base64) \
  --profile localstack \
  logs/lambda-output.log
```

## SNS

### SNSトピックの作成

```shell
aws --endpoint-url http://localhost:54566 sns create-topic \
  --region us-east-1 \
  --name testTopic \
  --profile localstack
```

### LambdaにSNSからの通知を設定

```shell
aws --endpoint-url http://localhost:54566 lambda add-permission \
  --region us-east-1 \
  --function-name testFunction \
  --statement-id sns \
  --action "lambda:InvokeFunction" \
  --principal sns.amazonaws.com \
  --source-arn arn:aws:sns:us-east-1:000000000000:testTopic \
  --profile localstack
```

### SNSトピックにLambdaをサブスクライブ

```shell
aws --endpoint-url http://localhost:54566 sns subscribe \
  --region us-east-1 \
  --topic-arn arn:aws:sns:us-east-1:000000000000:testTopic \
  --protocol lambda \
  --notification-endpoint arn:aws:lambda:us-east-1:000000000000:function:testFunction \
  --profile localstack
```

### SNSトピックにメッセージを送信

```shell
aws --endpoint-url http://localhost:54566 sns publish \
  --region us-east-1 \
  --topic-arn arn:aws:sns:us-east-1:000000000000:testTopic \
  --message 'Hello, SNS!' \
  --profile localstack
```
