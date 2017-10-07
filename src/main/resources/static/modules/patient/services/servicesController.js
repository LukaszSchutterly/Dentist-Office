(function () {
    angular.module("patientApp").controller("servicesController",function (dentalServicesService) {
        var sCtrl=this;
        sCtrl.dentalServices=dentalServicesService.dentalServicesList;


    })

})();