'use strict';

/**
 * @ngdoc function
 * @name photonApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the photonApp
 */
angular.module('photonApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
