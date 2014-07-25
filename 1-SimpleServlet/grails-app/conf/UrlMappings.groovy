class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?" {
			constraints {
				// apply constraints here
			}
		}

		"/" (controller: 'echo')
		
		"500"(view: '/error')
	}
}
