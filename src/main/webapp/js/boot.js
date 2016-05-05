(function() {
  "use strict";

  var app = angular.module('app', ['ngMaterial', 'ngMdIcons'])

  app.controller('AppController', function($scope, $mdDialog)
  {
    var ac = new AppController($scope)

    ac.loadUserState()

// TODO - begin
    $scope.askLogout = function()
    {
/*
      $mdDialog.show({
          clickOutsideToClose: true,
          scope: $scope,
          preserveScope: true,
          template: '<md-dialog style="position:fixed;top:10em;">' +
                    '<md-dialog-content>' +
                    '{{model.email}}' +
                    '</md-dialog-content>' +
                    '</md-dialog>',
          controller: function DialogController($scope, $mdDialog) {
            $scope.closeDialog = function() {
              $mdDialog.hide();
            }
          }
       })
*/
      var dialog = $mdDialog.alert()
        .clickOutsideToClose(true)
        .textContent('Сессия' + $scope.model.email)
        .ok('Выход');

      $mdDialog
          .show(dialog)
          .finally(function() {
            dialog = undefined;
          })
    }
// TODO - end

  })
})()
