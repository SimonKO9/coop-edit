define(['angular'], function(angular, controllers) {
    var dashboard = angular.module('dashboard', []);
    dashboard.controller('DashboardController', function($scope, $interval) {
        $scope.date = new Date().toString();

        $interval(function() {
            $scope.date = new Date().toString();
        }, 1000);
    });
});