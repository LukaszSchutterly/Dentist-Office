(function () {
    var app = angular.module("patientApp", ['ngRoute', 'ui.bootstrap', 'sharedServices']);

    app.controller("mainController", function ($scope, dentalServicesService, $location) {
        dentalServicesService.init();
        $scope.location = $location;

        $scope.$watch('location.url()', function (newVal) {
            $scope.path=newVal;
        });


    });

    app.config(function ($routeProvider, $locationProvider) {
        $routeProvider
            .when('/registration', {
                templateUrl: '/modules/patient/registration/registration.html'
            }).when('/localization', {
            templateUrl: '/modules/patient/localization/localization.html'
        }).when('/services', {
            templateUrl: '/modules/patient/services/services.html'
        });

        $locationProvider.hashPrefix("");
    });

})();