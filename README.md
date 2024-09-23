
## Usage 

### aws credentialsの設定
AWSのコンソールからIAMユーザーを作成し、SNS Publishの権限を付与する。
次にアクセスキーとシークレットキーを取得し、`~/.aws/credentials`に貼り付ける。

```
[sns-publisher]
aws_access_key_id = foo
aws_secret_access_key = foo
```

### `.jar`の作成
```shell
sbt assebmly
```
