(function () {
    var app=angular.module("patientApp",['ngRoute','ui.bootstrap','sharedServices']);

    app.config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/registration', {
                templateUrl: '/modules/patient/registration/registration.html'
            })
            .when('/localization', {
                templateUrl: '/modules/patient/localization/localization.html'
            });

        $locationProvider.hashPrefix("");
    });

})();