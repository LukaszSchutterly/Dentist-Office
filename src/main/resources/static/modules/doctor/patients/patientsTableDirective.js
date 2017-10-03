(function () {
    angular.module("doctorApp").directive("patientsTable", function () {

        return {
            templateUrl: "/templates/doctor/patients/patientsTableTemplate.html",
            controllerAs:"ptCtrl",
            controller:function ($scope,patientsViewService) {
                var ptCtrl=this;
                ptCtrl.sortType = 'lastName';
                ptCtrl.sortReverse = false;
                ptCtrl.patientsViewService=patientsViewService;
                ptCtrl.setPatient=patientsViewService.setPatient;

                $scope.$watch('ptCtrl.patientsViewService.selectedPatient',function (newVal) {
                    ptCtrl.patient=newVal;
                });

                $scope.$watch('ptCtrl.patientsViewService.patients',function (newVal) {
                    ptCtrl.patients=newVal;
                })

            }
        }
    })
})();