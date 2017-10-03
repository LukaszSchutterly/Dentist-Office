(function () {
    angular.module("doctorApp").directive("visitsTable", function () {

        return {
            templateUrl: "/templates/doctor/visits/visitsTableTemplate.html",
            controllerAs: "tCtrl",
            controller: function (VISIT_TIMES, $scope, visitsViewService) {
                var tCtrl = this;
                tCtrl.visitTimes = VISIT_TIMES;
                tCtrl.visitsViewService = visitsViewService;

                tCtrl.getRowClass = function (visitTime) {

                    if(tCtrl.visitMap)
                    var visit=tCtrl.visitMap[visitTime];

                    if (visit !== undefined) {

                        if (visitsViewService.selectedVisit && visit.id === visitsViewService.selectedVisit.id)
                            return 'selected';
                        else if (visit.accepted) {
                            return 'success';
                        }
                        else if (!visit.accepted)
                            return 'info';
                    }
                    else {

                        if (tCtrl.notConfirmedVisitTimes && tCtrl.notConfirmedVisitTimes.indexOf(visitTime) > -1)
                            return 'warning';
                        else if (tCtrl.availableVisitTimes && tCtrl.availableVisitTimes.indexOf(visitTime) === -1)
                            return 'inactive';
                    }
                };

                $scope.$watch('tCtrl.visitsViewService.visitMap', function (newValue) {
                    tCtrl.visitMap = newValue;
                });

                $scope.$watch('tCtrl.visitsViewService.availableVisitTimes', function (newValue) {
                    tCtrl.availableVisitTimes = newValue;
                });

                $scope.$watch('tCtrl.visitsViewService.notConfirmedVisitTimes', function (newValue) {
                    tCtrl.notConfirmedVisitTimes = newValue;
                });

            }

        }

    });

    angular.module("doctorApp").constant("VISIT_TIMES", ["08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
        "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"])

})();