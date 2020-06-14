package blog

import com.fasterxml.jackson.*
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val port = 8080
    // Configure the embedded server. Netty is our http server engine and it will listen on port 8080.
    // The double colon is a shorthand operator than denotes we're referencing a method
    val server = embeddedServer(Netty, port, module = Application::mainModule)

    server.start()
}

fun Application.mainModule() {
    install(ContentNegotiation) {
        // Jackson is a plugin with ktor and gradle that will allow us to parse and stringify JSON data
        // with our app server. The code below will set up formatted JSON output from our API
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    routing {
        get("/") {
            context.respond("Welcome" to "our Cat Hotel")
        }
        get("/{name}") {
            val name = context.parameters["name"]
            context.respond(mapOf("Cat name:" to name))
        }

    }
}