(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngMdIcons'])

  var sim = new Simulator();

  app.controller('AppController', function($scope) {
    $scope.model = {}
    $scope.lastRequest = null

    $scope.ml = {
      'selectDictionary': 'Выберите словарь'
    }

    $scope.isDictionaryValid = function(response) {
      return response && response.body && response.body.dictionary
    }

    $scope.isUserStateValid = function(response) {
      return response && response.body && response.body.dictionaries
    }

    $scope.post = function(action, data, callback) {
      $scope.lastRequest = [ action, data, callback ]

      sim.post(action, data, callback);
    }

    $scope.selectDictionary = function() {
      var m = { 'dictionary': { 'id': $scope.model.dictionary.id } }

      $scope.post('selectDictionary', m, function(response) {
        if ($scope.isDictionaryValid(response)) {
          $scope.model.dictionary = response.body.dictionary
          $scope.model.error = null
        } else {
          $scope.model.error = 1
        }

        $scope.$apply()
      })
    }

    $scope.post('getUserState', null, function(response) {
      if ($scope.isUserStateValid(response)) {
        $scope.model.dictionaries = response.body.dictionaries
        $scope.model.dictionary   = $scope.isDictionaryValid(response)
                                  ? response.body.dictionary : null
        $scope.model.error = null
      } else {
        $scope.model.error = 1
      }

      $scope.$apply()
    })
  })
})()
