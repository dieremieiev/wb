/*******************************************************************************
 * Main
 ******************************************************************************/

"use strict";

function WBService()
{
  this.m_clientID   = '271144171558-ms1cj2o3ab4q60vbam5dl5f3fjtel65j.apps.googleusercontent.com'
  this.m_scope      = 'https://www.googleapis.com/auth/userinfo.email'
  this.m_serviceURL = '//localhost:8888/_ah/api'
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
  var self = this

  setTimeout(function() {
    gapi.auth.authorize({
      client_id: self.m_clientID,
      scope    : self.m_scope,
      immediate: mode
    }, function(response) {
      if (response && !response.error && response.access_token) { callback() }
    });
  }, 0)
}

WBService.prototype.logout = function(callback)
{
  // TODO: do NOT work with localhost => check on real domain

  setTimeout(function() {
    gapi.auth.setToken(null)
    gapi.auth.signOut()
    callback()
  }, 0)
}

WBService.prototype.post = function(action, data, callback)
{
  setTimeout(function() {
    gapi.client.wordbook[action](data).execute(callback)
  }, 0)
}
