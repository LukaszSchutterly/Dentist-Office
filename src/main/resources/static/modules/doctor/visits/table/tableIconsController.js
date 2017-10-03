(function () {
    angular.module("doctorApp").controller("tableIconsController",
        function ($uibModal, visitService, daySchemeService, arrayUtilitiesService, dateService, visitsViewService) {
            var tiCtrl = this;

            tiCtrl.deleteAvailableVisitTime = function (visitTime) {
                var availableVisitTimes = visitsViewService.availableVisitTimes;
                var dayScheme = prepareDayScheme(visitsViewService.viewDate, availableVisitTimes);

                arrayUtilitiesService.removeElementFromArray(availableVisitTimes, visitTime);

                updateDayScheme(dayScheme)
            };

            tiCtrl.addAvailableVisitTime = function (visitTime) {
                var availableVisitTimes = visitsViewService.availableVisitTimes;
                var dayScheme = prepareDayScheme(visitsViewService.viewDate, availableVisitTimes);

                availableVisitTimes.push(visitTime);

                updateDayScheme(dayScheme)
            };

            function prepareDayScheme(date, availableVisitTimes) {
                var dayScheme = {};
                dayScheme.date = dateService.convertDateToValidRestString(date);
                dayScheme.availableVisitTimes = availableVisitTimes;

                return dayScheme;
            }

            function updateDayScheme(dayScheme) {

                daySchemeService.updateDayScheme(dayScheme).then(function () {

                }, function () {
                    console.log('Failed to update dayScheme')
                })
            }

            tiCtrl.deleteVisit = function (visit) {

                visitService.deleteVisit(visit).then(function () {
                    visitsViewService.updateView();
                }, function () {
                    console.log("Failed to delete visit")
                });
            };

            tiCtrl.addVisit = function (visitTime) {
                var modalInstance = $uibModal.open({
                    animation: tiCtrl.animationsEnabled,
                    templateUrl: 'templates/doctor/visits/addVisitModal.html',
                    controller: 'modalInstanceController',
                    controllerAs: 'miCtrl'
                });

                tiCtrl.toggleAnimation = function () {
                    tiCtrl.animationsEnabled = !tiCtrl.animationsEnabled;
                };

                modalInstance.result.then(function (visit) {
                    visit.visitTime = visitTime;
                    console.log(visit);

                    visitService.postVisit(visit).then(function () {
                        visitsViewService.updateView();
                    }, function () {
                        console.log("Failed to post visit")
                    })

                }, function () {
                    console.log("Modal canceled")
                });

            };

        });

})();