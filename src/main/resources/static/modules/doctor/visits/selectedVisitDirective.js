(function () {
    angular.module("doctorApp").directive("selectedVisit", function () {

        return {
            templateUrl: "/templates/doctor/visits/selectedVisitTemplate.html",
            controllerAs: "svCtrl",
            controller: function (VISIT_TIMES, $scope, visitService, visitsViewService,dentalServicesService) {
                var svCtrl = this;
                svCtrl.visitsViewService = visitsViewService;
                svCtrl.editingVisit = false;
                getDentalServicesList();

                svCtrl.editVisit = function () {
                    svCtrl.preEditVisit = JSON.parse(JSON.stringify(svCtrl.visit));
                    svCtrl.editingVisit = true;
                };

                svCtrl.cancelEdit = function () {
                    svCtrl.editingVisit = false;
                    visitsViewService.changeVisitMapElement(svCtrl.preEditVisit);
                    visitsViewService.selectedVisit=svCtrl.preEditVisit;
                };

                svCtrl.updateVisit = function () {

                    visitService.updateVisit(svCtrl.visit).then(function () {
                        svCtrl.editingVisit = false;
                        visitsViewService.updateView();
                    }, function () {
                        visitsViewService.changeVisitMapElement(svCtrl.preEditVisit);
                        visitsViewService.selectedVisit=svCtrl.preEditVisit;
                        console.log("Failed to update visit")
                    });
                };

                function getDentalServicesList() {

                    dentalServicesService.getDentalServicesList().then(function (data) {
                        svCtrl.dentalServices = data;
                    }, function () {
                        console.log("Failed to get dental services list")
                    });
                }

                $scope.$watch('svCtrl.visitsViewService.selectedVisit', function (newValue) {
                    svCtrl.visit = newValue;

                    if (svCtrl.preEditVisit && newValue !== svCtrl.preEditVisit.id) {
                        svCtrl.editingVisit = false;
                    }
                })
            }

        }

    })

})();