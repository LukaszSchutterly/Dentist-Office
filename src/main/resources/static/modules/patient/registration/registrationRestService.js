(function () {
    angular.module("patientApp").service("registrationRestService", function ($http) {
        var self = this;

        self.confirmToken = function (token) {

            return $http.get("/confirm?token=" + token).then(function (response) {
                return response.data;
            })
        };

        self.resendEmail = function (visit) {

            return $http.post("/resendMail", visit).then(function (response) {
                return response.date
            })
        };

        self.registerVisit = function (visit) {
            visit.visitDateTime = visit.visitDate + " " + visit.visitTime;

            return $http.post("/registration", visit).then(function (response) {
                return response.date
            })
        };

        self.getDaySchemeByDate = function (dateString) {

            return $http.get("/daySchemes/" + dateString).then(function (response) {
                return response.data
            })
        };

    });

})();