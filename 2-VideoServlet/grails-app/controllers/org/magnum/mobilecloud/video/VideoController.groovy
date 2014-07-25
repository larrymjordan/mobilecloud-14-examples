package org.magnum.mobilecloud.video

/**
* This simple VideoController allows clients to send HTTP POST
* requests with videos that are stored in memory using a list.
* Clients can send HTTP GET requests to receive a plain/test
* listing of the videos that have been sent to the servlet
* so far. Stopping the servlet will cause it to lose the history
* of videos that have been sent to it because they are stored
* in memory.
*
* @author jules
*
*/
class VideoController {
	
	public static final String VIDEO_ADDED = "Video added.";
	
	// An in-memory list that the servlet uses to store the
	// videos that are sent to it by clients
	ArrayList videos
	
    def index = {
		
		// Make sure and set the content-type header so that the client
		// can properly (and securely!) display the content that you send
		// back
		response.contentType = "text/plain"
		
		/**
		 * This part processes all of the HTTP GET requests routed to the
		 * controller. This logic loops through the lists
		 * of videos that have been sent to it and generates a plain/text
		 * list of the videos that is sent back to the client.
		 */
		if (request.method == 'GET') {
			//If there's no videos we notify the client with a message.
			if (!videos) {
				response.status = 404
				render "No videos found!"
			}
	
			// Loop through all of the stored videos and print them out
			// for the client to see.
			for (Video v in videos) {
				render "${v.name} : ${v.url} \n"
			}
		}
		
		/**
		 * This part handles all HTTP POST requests that are routed to the
		 * servlet by the web container.
		 *
		 * Sending a post to the servlet with 'name', 'duration', and 'url'
		 * parameters causes a new video to be created and added to the list of
		 * videos.
		 *
		 * If the client fails to send one of these parameters, the servlet generates
		 * an HTTP error 400 (Bad request) response indicating that a required request
		 * parameter was missing.
		 */
		if (request.method == 'POST') {
			
			// First, extract the HTTP request parameters that we are expecting
			// from either the URL query string or the url encoded form body
			String name = params.name
			String url = params.url
			Long duration = params.long("duration")
			
			// Now, the servlet has to look at each request parameter that the
			// client was expected to provide and make sure that it isn't null,
			// empty, etc.
			if (!name || !url || !duration || url.trim().length() < 10 || duration < 0) {
				
				// If the parameters pass our basic validation, we need to
				// send an HTTP 400 Bad Request to the client and give it
				// a hint as to what it got wrong.
				response.status = 400
				render "Missing ['name','duration','url']."
				
			} else {
				// It looks like the client provided all of the data that
				// we need, use that data to construct a new Video object.
				// Add the video to our in-memory list of videos.
				videos << new Video (name: name, url: url, duration: duration)
				
				// Let the client know that we successfully added the video
				// by writing a message into the HTTP response body
				render VIDEO_ADDED
			}
		}
	}
}
