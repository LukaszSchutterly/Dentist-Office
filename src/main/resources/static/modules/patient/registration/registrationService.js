(function () {
    angular.module("patientApp").service("registrationService", function () {
        var self = this;
        self.max = 3;
        self.registrationStage = 0;
        self.visit = {

        };

        self.nextStage = function () {
            self.registrationStage++;
        };

        self.prevStage = function () {
            self.registrationStage--;
        };

        self.polishDateString = function () {
            if (self.visit.visitDate)
                return dateService.convertDateStringToPolishString(self.visit.visitDate)
        }
    })

})();