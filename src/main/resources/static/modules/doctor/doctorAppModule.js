(function () {
    var app=angular.module("doctorApp",['ngRoute','ui.bootstrap','sharedServices']);

    app.controller("mainController",function (dentalServicesService) {
        dentalServicesService.init();
    });

    app.config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/modules/doctor/visits/visits.html'
            })
            .when("/patients",{
                templateUrl: '/modules/doctor/patients/patients.html'
            });

        $locationProvider.hashPrefix("");
    });

})();