package sns

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

object SnsPublisher {

  def main(args: Array[String]): Unit = {
    // Create aws client
    val snsClient =
      SnsClient.builder()
        .credentialsProvider(ProfileCredentialsProvider.create("sns-publisher"))
        .region(Region.US_EAST_1)
        .build()

    val topicArn = "arn:aws:sns:us-east-1:557789326598:TestForLamda"

    val publishRequest = PublishRequest.builder()
      .message("Hello, world!")
      .topicArn(topicArn)
      .build()

    val publishResponse = snsClient.publish(publishRequest)

    println(
      s"""
         |MessageId: ${publishResponse.messageId()}
         |""".stripMargin)
  }


}
