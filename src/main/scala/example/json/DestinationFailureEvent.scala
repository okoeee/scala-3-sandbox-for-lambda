package example.json

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
