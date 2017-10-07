(function () {
    angular.module("sharedServices").service("dentalServicesService", function ($http) {
        var self = this;

        self.init = function () {

            $http.get("/services").then(function (response) {
                self.dentalServicesList = response.data;
            })
        }

    })

})();