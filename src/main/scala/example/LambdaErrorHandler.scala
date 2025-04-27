package example

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import io.circe.generic.auto.*
import io.circe.jawn.decode

import java.io.InputStream

class LambdaErrorHandler extends RequestHandler[InputStream, Unit] {

  case class SnsData(
      Type:      String,
      MessageId: String,
      TopicArn:  String,
      Subject:   Option[String],
      Message:   String
  )
  case class SnsRecord(
      EventSource: String,
      Sns:         SnsData
  )
  case class OriginalRequestPayload(
      Records: List[SnsRecord]
  )
  case class RequestContext(
      requestId:   String,
      functionArn: String,
      condition:   String
  )
  case class DestinationFailureEvent(
      requestContext: RequestContext,
      requestPayload: OriginalRequestPayload
  )

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
