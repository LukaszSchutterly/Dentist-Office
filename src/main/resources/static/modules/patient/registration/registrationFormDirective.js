(function () {
    angular.module("patientApp").directive("registrationForm", function () {

        return {
            templateUrl: "/templates/patient/registration/registrationFormTemplate.html",
            controllerAs: "rfCtrl",
            controller: function ($scope,dateService, registrationService, dentalServicesService) {
                var rfCtrl = this;
                rfCtrl.visit = registrationService.visit;
                rfCtrl.phoneRegex = '[\-\+\(\)0-9]{8,15}';
                init();

                function init() {
                    dentalServicesService.getDentalServicesList().then(function (data) {
                        rfCtrl.dentalServices = data;
                    }, function () {
                        console.log("Failed to load dental services")
                    })
                }

                rfCtrl.polishDateString = function () {
                    return dateService.convertDateStringToPolishString(rfCtrl.visit.visitDate)
                };

            }
        }

    })

})();