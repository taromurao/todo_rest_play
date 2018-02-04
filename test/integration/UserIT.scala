package integration

import language.postfixOps
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration._

class UserIT extends PlaySpec with GuiceOneServerPerSuite {
  "Anybody" must {
    "be able to create his user account" in {
      val wsClient = app.injector.instanceOf[WSClient]
      val hostPort: String = s"localhost:$port"
      val uri: String = s"http://$hostPort/users"
      val data: JsObject = Json.obj(
        "email" -> "john@example.com",
        "password" -> "1234567890"
      )
      val request: WSRequest = wsClient
        .url(uri)
        .addHttpHeaders(
          "Content-Type" -> "application/json"
        )
        .withRequestTimeout(10000 millis)
      val response: WSResponse = Await.result(request.post(data), 1000 millis)

      response.status mustBe CREATED
    }
  }
//  Authorization: Digest username="Mufasa",
//                     realm="testrealm@host.com"

}
