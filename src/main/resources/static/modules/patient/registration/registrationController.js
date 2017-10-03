(function () {
    angular.module("patientApp").controller("registrationController",
        function ($scope,registrationService) {
            var rCtrl = this;
            rCtrl.registrationService=registrationService;
            rCtrl.nextStage =registrationService.nextStage;

            $scope.$watch("rCtrl.registrationService.registrationStage",function (newValue) {
                rCtrl.registrationStage=newValue;
            })

        });

})();