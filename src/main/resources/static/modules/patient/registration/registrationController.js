(function () {
    angular.module("patientApp").controller("registrationController",
        function ($scope,registrationService) {
            var rCtrl = this;
            rCtrl.registrationService=registrationService;

            $scope.$watch("rCtrl.registrationService.registrationStage",function (newValue) {
                rCtrl.registrationStage=newValue;
            });

            rCtrl.registerOnline=function () {
                registrationService.nextStage();
            }

        });

})();