package org.magnum.mobilecloud.video

import grails.test.*


/**
 * This test sends a POST request to the VideoController to add a new video and
 * then sends a second GET request to check that the video showed up in the list
 * of videos.
 * 
 * To run this test, execute on the grails console test-app org.magnum.mobilecloud.video.Video*
 * @author lmorales
 */
class VideoControllerTests extends ControllerUnitTestCase {
	
	/**
	 * This test sends a POST request to the VideoController to add a new video and
	 * then sends a second GET request to check that the video showed up in the
	 * list of videos.
	 *
	 * This is quite long for a test method, but is meant to show how to do
	 * a full add/list cycle interaction with the VideoServlet.
	 *
	 * @throws Exception
	 */
    void testVideoAddAndList() {
		//Initializes the videos list.
		controller.videos = []
		
		// Information about the video
		// We create a random String for the name so that we can ensure
		// that the video is added after every run of this test.
		def myRandomID = UUID.randomUUID().toString();
		controller.params.name = "Video - " + myRandomID;
		controller.params.url = "http://coursera.org/some/video-"+myRandomID;
		controller.params.duration = 60 * 10 * 1000; // 10min in milliseconds
		
		// Create the HTTP POST request to send the video to the server
		controller.request.method = "POST"
		
		//Emulate the HTTP request to the VideoController.
		controller.index()
		
		// Check that we got an HTTP 200 OK response code
		assert controller.response.status == 200
		
		// Make sure that the response is what we expect.
		assert VideoController.VIDEO_ADDED == controller.response.contentAsString
		
		// Now that we have posted the video to the server, we construct
		// an HTTP GET request to fetch the list of videos from the VideoController
		controller.request.method = "GET"
		
		//Emulate the HTTP request to the VideoController.
		controller.index()
		
		// Check that we got an HTTP 200 OK response code
		assert controller.response.status == 200
		
		// Construct a representation of the text that we are expecting
		// to see in the response representing our video
		String expectedVideoEntry = controller.params.name + " : " + controller.params.url;
		
		//Recieve the body of the response.
		String responseBody = controller.response.contentAsString
		
		// Check that our video shows up in the list by searching for the
		// expectedVideoEntry in the text of the response body
		assert responseBody.contains(expectedVideoEntry)
    }
	
	/**
	 * This test sends a POST request to the VideoController and supplies an
	 * empty String for the "name" parameter, which should cause the
	 * VideoServlet to generate an error 400 Bad request response.
	 *
	 * @throws Exception
	 */
	void testMissingRequestParameter() {
		// Information about the video
		// We create a random String for the title so that we can ensure
		// that the video is added after every run of this test.
		
		// We are going to purposely send an empty String for the title
		// in this test and ensure that the VideoServlet generates a 400
		// response code.
		controller.params.name = "";
		controller.params.duration = 60 * 10 * 1000; // 10min in milliseconds
		String myRandomID = UUID.randomUUID().toString();
		controller.params.url = "http://coursera.org/some/video-"+myRandomID;
		
		// Create the HTTP POST request to send the video to the server
		controller.request.method = "POST"
		
		//Emulate the HTTP request to the VideoController.
		controller.index()
		
		//The VideoController should generate an error 400 Bad request response
		assert 400 == controller.response.status
	}
}
