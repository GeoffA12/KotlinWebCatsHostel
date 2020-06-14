import blog.mainModule
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import junit.framework.Assert.assertEquals


import org.junit.Test

class ApplicationTest {

    @Test
    fun emptyPath() {
        withTestApplication(Application::mainModule) {
            val call = handleRequest(HttpMethod.Get, "")

            assertEquals(HttpStatusCode.OK, call.response.status())
        }
    }

    @Test
    fun validValue() {
        withTestApplication(Application::mainModule) {
            val call = handleRequest(HttpMethod.Get, "/Snowflake")
            // Jackson gives us access to a string to JSON mapping object we can utilize especially
            // when testing here


            // Convert the string response and parse it into JSON
            assertEquals(("""
                {
                  "Cat name:" : "Snowflake"
                }
                """.asJson()), (call.response.content?.asJson()))
        }
    }
}

private fun String.asJson() = ObjectMapper().readTree(this)