package example

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import example.json.{DestinationFailureEvent, InputData}
import io.circe.generic.auto.*
import io.circe.jawn.decode
import io.circe.parser

import java.io.InputStream

class LambdaErrorHandler extends RequestHandler[InputStream, Unit] {

  override def handleRequest(inputStream: InputStream, context: Context): Unit = {
    val inputString = scala.io.Source.fromInputStream(inputStream).mkString

    decode[DestinationFailureEvent](inputString) match {
      case Right(event) =>
        println(s"Successfully parsed event: $event")
        event.requestPayload.Records.foreach { record =>
          println(s"Record: ${record}")
          println(s"Message: ${record.Sns.Message}")
          decode[InputData](record.Sns.Message) match {
            case Right(data) =>
              println(s"Successfully parsed InputData with id: ${data.id}")
            case Left(error) =>
              println(s"Failed to parse SNS message as InputData: ${error.getMessage}")
          }
        }
      case Left(error)  =>
        println(inputString)
        println(s"Failed to parse input as DestinationFailureEvent: ${error.getMessage}")
    }
  }

}
