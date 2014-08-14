define(['angular', 'angular-route', './dashboard/main'], function (angular) {
    return angular.module('app', ['ngRoute', 'dashboard'])
        .config(function ($routeProvider) {
            $routeProvider
                .when('/home', {
                controller: 'DashboardController',
                templateUrl: '/assets/partials/dashboard.html'
            })
                .otherwise({
                    redirectTo: '/home'
                })
        });

});