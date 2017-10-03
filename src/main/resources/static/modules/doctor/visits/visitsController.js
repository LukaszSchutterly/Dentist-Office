(function () {
    angular.module("doctorApp").controller("visitsController",
        function (VISIT_TIMES, $scope, daySchemeService, dateService, visitsViewService,patientsViewService) {
            var vCtrl = this;
            vCtrl.visitsViewService=visitsViewService;
            vCtrl.patientsViewService=patientsViewService;
            var date=visitsViewService.viewDate;

            vCtrl.previous = function () {
                visitsViewService.updateView(dateService.changeDateByGivenNumberOfDays(date, -1));
            };

            vCtrl.next = function () {
                visitsViewService.updateView(dateService.changeDateByGivenNumberOfDays(date, 1));
            };

            vCtrl.blockDay = function () {
                var dayScheme = {};
                dayScheme.date = dateService.convertDateToValidRestString(date);
                dayScheme.availableVisitTimes = [];

                daySchemeService.updateDayScheme(dayScheme).then(function () {
                    visitsViewService.updateView();
                }, function () {
                    console.log('Failed to block day')
                })

            };

            $scope.$watch('vCtrl.visitsViewService.viewDate', function (newValue) {
                date=newValue;
                vCtrl.polishViewDateString = dateService.convertDateToPolishString(date);
            });

        }
    );

})();