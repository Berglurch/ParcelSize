package test.java

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Performance extends Simulation {

	val httpProtocol = http
		.baseURL("http://192.168.50.105:8443")
		.inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0")

	val headers_2 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Access-Control-Request-Headers" -> "content-type",
		"Access-Control-Request-Method" -> "POST",
		"Origin" -> "http://192.168.50.105:8080")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Content-Type" -> "application/json;charset=utf-8",
		"Origin" -> "http://192.168.50.105:8080")

    val uri1 = "192.168.50.105"

	val scn = scenario("Performance")
		.exec(http("request_0")
			.get("http://" + uri1 + ":8080/images/icon_pakete.png")
			.resources(http("request_1")
			.get("http://" + uri1 + ":8080/images/icon_pakete.png")
			.check(status.is(404)))
			.check(status.is(404)))
		.pause(11)
		.exec(http("request_2")
			.options("/parcel/size")
			.headers(headers_2)
			.resources(http("request_3")
			.post("/parcel/size")
			.headers(headers_3)
			.body(RawFileBody("Performance_0003_request.txt"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}