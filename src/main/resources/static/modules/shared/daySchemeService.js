(function () {
    angular.module("sharedServices").service("daySchemeService", function ($http) {
        var self = this;
        self.url = "/daySchemes/";

        self.getDaySchemeByDate = function (dateString) {

            return $http.get(self.url + dateString).then(function (response) {
                return response.data
            })
        };

        self.updateDayScheme = function (dayScheme) {
            return $http.put(self.url, dayScheme)
        };

        self.getNotConfirmedVisitTimes = function (dateString) {
            return $http.get(self.url + "notConfirmedVisitTimes?date=" + dateString).then(function (response) {
                return response.data
            })
        }

    });

})();