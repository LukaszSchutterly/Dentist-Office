(function () {
    angular.module("doctorApp").service("patientsViewService", function (patientService, visitService) {
        var self = this;

        self.setPatient = function (patient) {
            self.selectedPatient = patient;
            getPatientVisits(patient);
        };

        self.getAllPatients = function (patient) {

            patientService.getAllPatients().then(function (data) {
                self.patients = data;
                if(patient)
                    self.setPatient(patient);
                else
                    self.setPatient(self.patients[0]);

            }, function () {
                console.log("Failed to load patient list")
            })
        };

        self.getAllPatients();

        function getPatientVisits(patient) {

            visitService.getVisitsByPatient(patient).then(function (data) {
                self.visits = data;
            }, function () {
                console.log("Failed to get patient's visits")
            })
        }

    })

})();