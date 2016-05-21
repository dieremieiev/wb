function init() { window.init() }

(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngSanitize', 'ngMdIcons'])

  app.controller('AppController', function($scope, $mdDialog, $timeout, $window)
  {
    $scope.ac = new AppController($scope, $mdDialog, $timeout)

    $window.init = function() { $scope.ac.init() }
  })

  // TODO
  // app.directive('ngEnter', function () {
  //   return function (scope, element, attrs) {
  //     element.bind("keydown keypress", function (event) {
  //         if(event.which === 13) {
  //             scope.$apply(function () {
  //                 scope.$eval(attrs.ngEnter)
  //             })
  //             event.preventDefault();
  //         }
  //     })
  //   }
  // })
})()
