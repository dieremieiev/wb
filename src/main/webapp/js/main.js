var SERVICEURL = 'http://localhost/wb';

(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngMdIcons'])

  var simulator = new Simulator();

  app.controller('AppController', function($scope, $http) {
    $scope.setDictionary = function() {
      // TODO: send request to server
    }

    $scope.post = function(action, success, error) {
      // TODO: $http
      simulator.post(SERVICEURL, { action: action })
               .then(success, error);
    }

    $scope.model = null

    $scope.post('getUserState', function(response) {
      $scope.model = response.data.body
      $scope.$apply()
    }, function(response) {
      // TODO - ERROR
    })

    $scope.ml = {
      'selectDictionary': 'Выберите словарь'
    }
  })
})()
