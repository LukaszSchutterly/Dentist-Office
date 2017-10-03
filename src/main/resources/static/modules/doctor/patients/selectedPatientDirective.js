(function () {
    angular.module("doctorApp").directive("selectedPatient", function () {

        return {
            templateUrl: "/templates/doctor/patients/selectedPatientTemplate.html",
            controllerAs: "spCtrl",
            controller: function ($scope, patientsViewService,patientService, arrayUtilitiesService) {
                var spCtrl = this;
                spCtrl.editingPatient = false;
                spCtrl.patientsViewService = patientsViewService;

                spCtrl.popover = {
                    isOpen: false,
                    templateUrl: 'templates/doctor/patients/deletePatientPopover.html',
                    changePopoverState: function () {
                        spCtrl.popover.isOpen = !spCtrl.popover.isOpen;
                    }
                };

                spCtrl.removePatient = function (patient) {

                    patientService.removePatient(patient).then(function () {
                        spCtrl.popover.changePopoverState();
                        arrayUtilitiesService.removeElementFromArray(spCtrl.patients, patient);
                        patientsViewService.selectedPatient=undefined;
                    }, function () {
                        console.log("Failed to delete patient")
                    })
                };

                spCtrl.editPatient = function () {
                    spCtrl.preEditPatient = JSON.parse(JSON.stringify(spCtrl.patient));
                    spCtrl.editingPatient = true;
                };

                spCtrl.cancelEdit = function () {
                    spCtrl.editingPatient = false;
                    arrayUtilitiesService.replaceElementInArray(spCtrl.patients, spCtrl.patient, spCtrl.preEditPatient);
                    spCtrl.patient = spCtrl.preEditPatient;
                };

                spCtrl.updatePatient = function () {

                    patientService.updatePatient(spCtrl.patient).then(function () {
                        spCtrl.editingPatient = false;
                        patientsViewService.getAllPatients(spCtrl.patient)
                    }, function () {
                        arrayUtilitiesService.replaceElementInArray(spCtrl.patients, spCtrl.patient, spCtrl.preEditPatient);
                        spCtrl.patient = spCtrl.preEditPatient;
                        console.log("Failed to update patient")
                    })
                };

                $scope.$watch('spCtrl.patientsViewService.selectedPatient', function (newValue) {
                    spCtrl.patient = newValue;
                });

                $scope.$watch('spCtrl.patientsViewService.patients', function (newValue) {
                    spCtrl.patients = newValue;
                })

            }
        }
    })

})();