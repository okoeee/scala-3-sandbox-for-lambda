package sns

import org.scalatest.freespec.AnyFreeSpec
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

class SnsPublisherTest extends AnyFreeSpec {

  "SnsPublisher" - {
    "publish" in {

      val snsClient = SnsClient.builder()
        .endpointOverride(new java.net.URI("http://localhost:54566"))
        .credentialsProvider(ProfileCredentialsProvider.create("localstack"))
        .region(Region.US_EAST_1)
        .build()

      val topicArn = "arn:aws:sns:us-east-1:000000000000:testTopic"

      val message = "Hello, world!"

      SnsPublisher.publish(snsClient, topicArn, message)

    }
  }

}
