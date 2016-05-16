(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngSanitize', 'ngMdIcons'])

  app.controller('AppController', function($scope, $mdDialog)
  {
    var ac = new AppController()

    ac.init($scope, $mdDialog)

    $scope.askLogout        = function() { ac.askLogout() }
    $scope.checkWord        = function() { ac.checkWord() }
    $scope.login            = function() { ac.login() }
    $scope.nextWord         = function() { ac.nextWord() }
    $scope.repeatRequest    = function() { ac.repeatRequest() }
    $scope.selectDictionary = function() { ac.selectDictionary() }
    $scope.suggestWord      = function() { ac.suggestWord() }
  })
})()
