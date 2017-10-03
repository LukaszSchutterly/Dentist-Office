(function () {
    angular.module("sharedServices").service("patientService", function ($http) {
        var self = this;
        self.url = "/patients";

        self.getAllPatients = function () {
            return $http.get(self.url)
                .then(function (response) {
                    return response.data;
                })
        };

        self.removePatient=function (patient) {
            return $http.delete(self.url+"/"+patient.id)
        };

        self.updatePatient=function (patient) {
            return $http.put(self.url,patient)
        };

    });

})();