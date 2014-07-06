'use strict';

/**
 * @ngdoc function
 * @name photonApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the photonApp
 */
angular.module('photonApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
