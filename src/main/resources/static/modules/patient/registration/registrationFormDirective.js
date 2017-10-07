(function () {
    angular.module("patientApp").directive("registrationForm", function () {

        return {
            templateUrl: "/templates/patient/registration/registrationFormTemplate.html",
            controllerAs: "rfCtrl",
            controller: function ($scope,dateService, registrationService, dentalServicesService) {
                var rfCtrl = this;
                rfCtrl.visit = registrationService.visit;
                rfCtrl.registrationService=registrationService;
                rfCtrl.phoneRegex = '[\-\+\(\)0-9]{8,15}';
                rfCtrl.dentalServices=dentalServicesService.dentalServicesList;

                rfCtrl.polishDateString = function () {
                    return dateService.convertDateStringToPolishString(rfCtrl.visit.visitDate)
                };

            }
        }

    })

})();