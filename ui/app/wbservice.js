/*******************************************************************************
 * Main
 ******************************************************************************/

"use strict";

function WBService()
{
  if (location && location.hostname == 'localhost') {
    // dev
    this.m_clientID   = '271144171558-ms1cj2o3ab4q60vbam5dl5f3fjtel65j.apps.googleusercontent.com'
    this.m_serviceURL = '//localhost:8888/_ah/api'
  } else {
    // prod
    this.m_clientID   = '271144171558-0nbds1repidmhbacroal6u6f631rpel6.apps.googleusercontent.com'
    this.m_serviceURL = 'https://wb19032016.appspot.com/_ah/api'
  }

  this.m_scope = 'https://www.googleapis.com/auth/userinfo.email'
}

WBService.prototype.init = function(callback)
{
  var apisToLoad = 2;

  var self = this

  function initCallback() {
    if (--apisToLoad == 0) { self.login(true, callback) }
  }

  gapi.client.load('wordbook', 'v1', initCallback, this.m_serviceURL);
  gapi.client.load('oauth2'  , 'v2', initCallback);
}


/*******************************************************************************
 * Public
 ******************************************************************************/

WBService.prototype.login = function(mode, callback)
{
  gapi.auth.authorize({
    client_id     : this.m_clientID,
    scope         : this.m_scope,
    immediate     : mode
  }, function(response) {
    callback(response && !response.error && response.access_token)
  });
}

WBService.prototype.logout = function()
{
  gapi.auth.signOut()
  gapi.auth.setToken(null)
}

WBService.prototype.post = function(action, data, callback)
{
  gapi.client.wordbook[action](data).execute(callback)
}
