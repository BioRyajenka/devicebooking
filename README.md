Simple device booking service. 

Supports booking, returning and listing devices. 
Currently service uses embedded h2 database. REST API is available under http://localhost:8080/swagger-ui.html

The project is splitted into 4 modules:
 * api     - main application
 * dto     - dto classes
 * types   - domain entity classes (may be published/used in dtos)
 * fonoapi - simple fonoapi api implementation

Nice to haves, which are not implemented:
 * More tests
 * Docker/docker-compose integration
 * HTTP Client swagger generation / manual implementation
 * Unified http response in case of errors
