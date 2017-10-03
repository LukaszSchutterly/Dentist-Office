(function () {
    angular.module("doctorApp").directive("patientVisits", function () {

        return {
            templateUrl: "templates/doctor/patients/patientVisitsTemplate.html",
            controllerAs: "pvCtrl",
            controller: function ($scope, patientsViewService, arrayUtilitiesService, visitService, dentalServicesService, dateService) {
                var pvCtrl = this;
                pvCtrl.patientsViewService = patientsViewService;
                pvCtrl.editingVisit = false;
                getDentalServicesList();

                pvCtrl.editVisit = function (visit) {
                    pvCtrl.visit = visit;
                    pvCtrl.preEditVisit = JSON.parse(JSON.stringify(visit));
                    pvCtrl.editingVisit = true;
                };

                pvCtrl.cancelEdit = function () {
                    pvCtrl.editingVisit = false;
                    arrayUtilitiesService.replaceElementInArray(pvCtrl.visits, pvCtrl.visit, pvCtrl.preEditVisit);
                    pvCtrl.visit = pvCtrl.preEditVisit;
                };

                pvCtrl.updateVisit = function () {

                    visitService.updateVisit(pvCtrl.visit).then(function () {
                        pvCtrl.editingVisit = false;

                    }, function () {
                        arrayUtilitiesService.replaceElementInArray(pvCtrl.visits, pvCtrl.visit, pvCtrl.preEditVisit);
                        pvCtrl.visit = pvCtrl.preEditVisit;
                        console.log("Failed to update visit")
                    });
                };

                pvCtrl.convertDateStringToPolishString = function (dateString) {
                    return dateService.convertDateStringToPolishString(dateString);
                };

                function getDentalServicesList() {

                    dentalServicesService.getDentalServicesList().then(function (data) {
                        pvCtrl.dentalServices = data;
                    }, function () {
                        console.log("Failed to get dental services list")
                    });
                }

                $scope.$watch('pvCtrl.patientsViewService.visits', function (newValue) {
                    pvCtrl.visits = newValue;
                })

            }
        }

    })

})();