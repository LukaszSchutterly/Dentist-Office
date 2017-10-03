(function () {
    angular.module('doctorApp').controller('modalInstanceController',
        function ($uibModalInstance, dentalServicesService, visitsViewService, dateService) {
            var miCtrl = this;
            miCtrl.visit = {patient: {}};
            getDentalServicesList();

            miCtrl.ok = function () {
                miCtrl.visit.visitDate = dateService.convertDateToValidRestString(visitsViewService.viewDate);

                $uibModalInstance.close(miCtrl.visit);
            };

            miCtrl.cancel = function () {
                $uibModalInstance.dismiss();
            };

            function getDentalServicesList() {

                dentalServicesService.getDentalServicesList().then(function (data) {
                    miCtrl.dentalServices = data;
                }, function () {
                    console.log("Failed to get dental services list")
                });
            }
        });

})();