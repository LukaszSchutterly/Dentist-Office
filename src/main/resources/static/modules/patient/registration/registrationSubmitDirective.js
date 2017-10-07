(function () {
    angular.module("patientApp").directive("registrationSubmit", function () {

        return {
            templateUrl: "/templates/patient/registration/registrationSubmitTemplate.html",
            controllerAs: "rsCtrl",
            controller: function (dateService, registrationService, registrationRestService) {
                var rsCtrl = this;
                rsCtrl.submited = false;
                rsCtrl.visit = registrationService.visit;
                rsCtrl.registrationService=registrationService;

                rsCtrl.submit = function () {
                    rsCtrl.submited = true;
                    rsCtrl.loading = true;

                    registrationRestService.registerVisit(rsCtrl.visit).then(function () {
                        rsCtrl.loading = false;
                    }, function () {
                        rsCtrl.serverError = true;
                        rsCtrl.loading = false;
                    })
                };

                rsCtrl.confirmToken = function () {

                    registrationRestService.confirmToken(rsCtrl.token).then(function (data) {
                        rsCtrl.tokenConfirmed = data;

                        if (rsCtrl.tokenConfirmed)
                            registrationService.nextStage();

                    }, function () {
                        rsCtrl.serverError = true;
                    })
                };

                rsCtrl.resendEmail = function () {

                    registrationRestService.resendEmail(rsCtrl.visit).then(function () {
                        rsCtrl.tokenConfirmed = undefined;
                    }, function () {
                        rsCtrl.serverError = true;
                    })
                };

                rsCtrl.getPanelClass = function () {

                    if (rsCtrl.serverError || rsCtrl.tokenConfirmed === false)
                        return 'panel-danger';
                    else
                        return 'panel-success'
                };

                rsCtrl.polishDateString = function () {
                    return dateService.convertDateStringToPolishString(rsCtrl.visit.visitDate)
                }
            }

        }

    })

})();