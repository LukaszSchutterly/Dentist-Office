(function () {
    angular.module("sharedServices").service("dentalServicesService", function ($http) {
        var self = this;

        self.getDentalServicesList = function () {

            return $http.get("/services").then(function (response) {
                return response.data;
            })
        }

    })

})();