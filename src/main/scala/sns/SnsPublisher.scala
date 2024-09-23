package sns

import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

object SnsPublisher {

  def publish(
      snsClient: SnsClient,
      topicArn: String,
      message: String
  ): Unit = {

    val publishRequest = PublishRequest.builder()
      .message(message)
      .topicArn(topicArn)
      .build()

    val publishResponse = snsClient.publish(publishRequest)

    println(
      s"""
         |MessageId: ${publishResponse.messageId()}
         |""".stripMargin)
  }


}
