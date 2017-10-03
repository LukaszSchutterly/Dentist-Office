(function () {
    angular.module("doctorApp").controller("patientsController", function ($scope, patientsViewService,visitsViewService) {
        var pCtrl = this;
        pCtrl.patientsViewService = patientsViewService;
        pCtrl.visitsViewService=visitsViewService;

        $scope.$watch("pCtrl.patientsViewService.selectedPatient", function (newValue) {
            pCtrl.patient=newValue;
        })


    });

})();