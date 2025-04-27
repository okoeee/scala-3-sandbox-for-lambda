package example

import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import io.circe.generic.auto.*
import io.circe.jawn.decode

class LambdaHandler extends RequestHandler[SNSEvent, Unit] {

  case class InputData(
      id: Long
  )

  override def handleRequest(event: SNSEvent, context: Context): Unit = {
    event.getRecords.forEach { record =>
      val message = record.getSNS.getMessage
      println(s"Received SNS message: $message")

      decode[InputData](message) match {
        case Right(data) =>
          println(s"Successfully parsed InputData with id: ${data.id}")
          throw new RuntimeException("Simulated error") // Simulate an error
        case Left(error) =>
          println(s"Failed to parse SNS message as InputData: ${error.getMessage}")
      }
    }
  }

}
