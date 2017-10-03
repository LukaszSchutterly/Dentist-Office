(function () {
    angular.module("patientApp").directive("visitTermChooser", function () {

        return {
            templateUrl: "/templates/patient/registration/visitTermChooserTemplate.html",
            controllerAs: "vtcCtrl",
            controller: function ($scope, dateService, registrationRestService, registrationService) {
                var vtcCtrl = this;
                vtcCtrl.viewDate = new Date();
                vtcCtrl.viewStartDate = new Date();
                vtcCtrl.registrationService=registrationService;
                init();

                vtcCtrl.changeViewDate = function (number) {
                    vtcCtrl.viewDate.setDate(vtcCtrl.viewDate.getDate() + number);
                };

                function init() {
                    vtcCtrl.viewDate.setDate(vtcCtrl.viewDate.getDate() + 2);
                    vtcCtrl.viewStartDate.setDate(vtcCtrl.viewStartDate.getDate() + 2);
                    updateData();
                }

                function updateData() {
                    vtcCtrl.restStringDate = dateService.convertDateToValidRestString(vtcCtrl.viewDate);
                    vtcCtrl.viewDateString = dateService.convertDateToPolishString(vtcCtrl.viewDate);

                    return registrationRestService.getDaySchemeByDate(vtcCtrl.restStringDate).then(function (data) {
                        registrationService.visit.visitDate = vtcCtrl.restStringDate;
                        vtcCtrl.availableVisitTimes = data.availableVisitTimes;
                    }, function () {
                        vtcCtrl.error = "Rejestracja w tym dniu nie jest możliwa";
                        vtcCtrl.availableVisitTimes = undefined;
                    });
                }

                $scope.$watch('vtcCtrl.viewDate', function (newValue, oldValue) {
                    vtcCtrl.visitTime = undefined;
                    vtcCtrl.error = undefined;

                    updateData().then(function () {

                        if (vtcCtrl.availableVisitTimes && vtcCtrl.availableVisitTimes.length === 0 && !vtcCtrl.error) {
                            var dateChangeNumber=newValue >= oldValue ? 1 : -1;

                            vtcCtrl.changeViewDate(dateChangeNumber);
                            vtcCtrl.viewStartDate.setDate(vtcCtrl.viewStartDate.getDate() + dateChangeNumber);
                            updateData();
                        }
                    });
                }, true);

                $scope.$watch('vtcCtrl.visitTime', function (newValue) {
                    registrationService.visit.visitTime = newValue;
                });

            }

        }

    })

})();