# wb

gradle appengineEndpointsInstallClientLibs

# Signing out

There is no concept of signing out using the JS client library, but you can emulate the behavior if you wish by any of these methods:

- Resetting the UI to the pre signed-in state.
- Setting any signed-in variables in your application logic to false
- Calling gapi.auth.setToken(null);
