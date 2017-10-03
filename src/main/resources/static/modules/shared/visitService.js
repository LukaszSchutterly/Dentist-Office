(function () {

    angular.module("sharedServices").service("visitService", function ($http) {
        var self = this;
        self.url = "/visits";

        self.postVisit = function (visit) {
            return $http.post(self.url, visit)
        };

        self.getVisitsByDate = function (dateString) {
            return $http.get(self.url + "/byDate/" + dateString).then(function (response) {
                    return response.data
                })
        };

        self.getVisitsByAccepted = function (accepted) {
            return $http.get(self.url + "/byAccepted?accepted=" + accepted).then(function (response) {
                    return response.data
                })
        };

        self.deleteVisit = function (visit) {
            return $http.delete(self.url + "/" + visit.id)
        };

        self.updateVisit = function (visit) {
            return $http.put(self.url, visit)
        };

        self.getVisitsByPatient = function (patient) {
            return $http.get(self.url + "/byPatient/" + patient.id).then(function (response) {
                    return response.data
                })
        };

        self.acceptOrRejectVisit = function (visit) {
            return $http.put(self.url + "/acceptOrReject", visit).then(function (response) {
                    return response.data
                })
        };

    });

})();