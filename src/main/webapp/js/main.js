(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngMdIcons'])

  app.controller('AppController', function($scope) {
    $scope.model = {
      //'dictionaryId': '4a0c7667-d971-45d0-8309-0e475a063164',
      'dictionaries': [
        { id: '5f6b2f41-d144-42aa-8a18-5a9e5e344f04', name: 'Словарь: 100 основных слов' },
        { id: '8a6d2093-57fb-41be-9517-fb4cad8229c2', name: 'Словарь: Числа' },
        { id: '4a0c7667-d971-45d0-8309-0e475a063164', name: 'Словарь: Дом' },
        { id: '6eff9378-beb1-4527-b8c4-9354396a2c32', name: 'Словарь: Транспорт' }
      ]
    }

    $scope.setDictionary = function() {
      // TODO: send request to server
    }

    $scope.ml = {
      'selectDictionary': 'Пожалуйста выберите словарь'
    }
  })
})()
