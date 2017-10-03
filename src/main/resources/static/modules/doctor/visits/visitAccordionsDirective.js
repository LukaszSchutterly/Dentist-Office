(function () {
    angular.module("doctorApp").directive("visitAccordions", function () {
        return {
            templateUrl: "/templates/doctor/visits/visitAccordions.html",
            controllerAs: "vaCtrl",
            controller: function ($scope, visitService, dateService, visitsViewService) {
                var vaCtrl = this;
                vaCtrl.visitsViewService = visitsViewService;
                vaCtrl.isAccordionOpen = [];
                getNotAcceptedVisits();
                setInterval(getNotAcceptedVisits, 60000);

                vaCtrl.accept = function (visit) {
                    visit.accepted = true;

                    visitService.acceptOrRejectVisit(visit).then(function () {
                        getNotAcceptedVisits();
                        visitsViewService.updateView();
                    }, function () {
                        console.log("Failed to update visit")
                    });
                };

                vaCtrl.reject = function (visit) {
                    visit.accepted = false;

                    visitService.acceptOrRejectVisit(visit).then(function () {
                        getNotAcceptedVisits();
                        visitsViewService.updateView();
                    }, function () {
                        console.log("Failed to delete visit");
                    });
                };

                vaCtrl.show = function (visit) {
                    var date = dateService.convertStringToDate(visit.visitDate);

                    visitsViewService.setVisitForView(visit);
                    visitsViewService.updateView(date);
                };

                vaCtrl.convertDateStringToPolishString = function (visit) {
                    return dateService.convertDateStringToPolishString(visit.visitDate) + ' ' + visit.visitTime;
                };

                function getNotAcceptedVisits() {

                    visitService.getVisitsByAccepted(false).then(function (data) {
                            vaCtrl.visitApplications = data
                        }, function () {
                            console.log("Failed to get visit applications")
                        }
                    )
                }

                $scope.$watch('vaCtrl.visitsViewService.selectedVisit.id', function (newValue, oldValue) {
                    vaCtrl.isAccordionOpen[newValue] = true;
                    vaCtrl.isAccordionOpen[oldValue] = false;
                })

            }
        }

    });

})();