package example

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import example.json.DestinationFailureEvent
import io.circe.generic.auto.*
import io.circe.jawn.decode

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
        }
      case Left(error)  =>
        println(inputString)
        println(s"Failed to parse input as DestinationFailureEvent: ${error.getMessage}")
    }
  }

}
