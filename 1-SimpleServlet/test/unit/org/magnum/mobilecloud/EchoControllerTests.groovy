package org.magnum.mobilecloud

import grails.test.*

class EchoControllerTests extends ControllerUnitTestCase {
	
	/**
	 * A test for the echo controller's index action.
	 * The index action receive a msg as a request 
	 * parameter and then return that message to the
	 * client with the prefix "Echo:" appended to it.
	 */
    void testEchoMessage() {
		controller.params.msg = "1234"
		controller.index()
		assert "Echo: 1234" == controller.response.contentAsString
    }
}
