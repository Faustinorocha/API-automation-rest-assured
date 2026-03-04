## Known Issues

The `/user/login` endpoint from Swagger Petstore does not validate credentials.

Even when invalid username and password are provided, the API returns:

200 OK

Example response:

{
  "code": 200,
  "type": "unknown",
  "message": "logged in user session:xxxxx"
}

Because of this behavior, negative login validation cannot be implemented.
