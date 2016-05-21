function init() { window.init() }

(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngSanitize', 'ngMdIcons'])

  app.directive('ngEnter', function () {
    return function(scope, element, attrs) {
      element.bind("keydown keypress", function(event) {
          if (event.which === 13) {
            scope.ac.onEnter()
            event.preventDefault();
          }
      })
    }
  })

  app.controller('AppController', function($scope, $mdDialog, $timeout, $window)
  {
    $scope.ac = new AppController($scope, $mdDialog, $timeout)

    $window.init = function() { $scope.ac.init() }
  })
})()
