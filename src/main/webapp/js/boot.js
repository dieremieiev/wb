(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngSanitize', 'ngMdIcons'])

  app.controller('AppController', function($scope, $mdDialog)
  {
    var ac = new AppController($scope, $mdDialog)

    ac.loadUserState()
  })
})()
