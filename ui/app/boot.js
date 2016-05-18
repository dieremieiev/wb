function init() { window.init() }

(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngSanitize', 'ngMdIcons'])

  app.controller('AppController', function($scope, $mdDialog, $timeout, $window)
  {
    var ac = new AppController($scope, $mdDialog, $timeout)

    $scope.askLogout        = function() { ac.askLogout() }
    $scope.checkWord        = function() { ac.checkWord() }
    $scope.login            = function() { ac.login() }
    $scope.nextWord         = function() { ac.nextWord() }
    $scope.repeatRequest    = function() { ac.repeatRequest() }
    $scope.selectDictionary = function() { ac.selectDictionary() }
    $scope.suggestWord      = function() { ac.suggestWord() }

    $window.init = function() { ac.init() }
  })
})()
