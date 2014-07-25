package org.magnum.mobilecloud

class EchoController {
	
	/**
	 * Default action for the echo controller. It receives a msg request
	 * parameter sent by the client and returns that msg with the "Echo:"
	 * prefix appended to it. The response's content type is in a text plain
	 * format.
	 */
    def index = {
		// Set the content type header that is going to be returned in the
		// HTTP response so that the client will know how to display the
		// result.
		response.contentType = "text/plain"
		// Echo a response back to the client with the msg that was sent
		render "Echo: ${params?.msg}" 
	}
}
