(function () {
    angular.module('doctorApp').controller('modalInstanceController',
        function ($uibModalInstance, dentalServicesService, visitsViewService, dateService) {
            var miCtrl = this;
            miCtrl.visit = {patient: {}};
            miCtrl.dentalServices = dentalServicesService.dentalServicesList;

            miCtrl.ok = function () {
                miCtrl.visit.visitDate = dateService.convertDateToValidRestString(visitsViewService.viewDate);

                $uibModalInstance.close(miCtrl.visit);
            };

            miCtrl.cancel = function () {
                $uibModalInstance.dismiss();
            };

        });

})();