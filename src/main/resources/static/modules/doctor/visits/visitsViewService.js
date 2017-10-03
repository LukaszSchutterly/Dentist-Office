(function () {
    angular.module("doctorApp").service("visitsViewService", function (visitService, daySchemeService, dateService) {
        var self = this;
        self.viewDate = new Date();
        setInterval(self.updateView, 60000);

        self.updateView = function (date) {

            if (date)
                self.viewDate = date;

            getVisits();
            getDayScheme();
            getNotConfirmedVisitTimes();
        };

        self.updateView();

        self.setVisitForView = function (visit) {
            self.selectedVisit = visit;
        };

        self.changeVisitMapElement = function (visit) {
            self.visitMap[visit.visitTime] = visit;
        };

        self.isVisitTimeTaken = function (visitTime) {
            if (self.availableVisitTimes) {
                return self.availableVisitTimes.indexOf(visitTime) === -1;
            }
        };

        function getVisits() {
            var restDateString = dateService.convertDateToValidRestString(self.viewDate);

            visitService.getVisitsByDate(restDateString).then(function (data) {
                self.visitMap = {};

                data.forEach(function (visit) {
                    self.visitMap[visit.visitTime] = visit;
                });
            }, function () {
                console.log("Failed to load visits")
            })
        }

        function getDayScheme() {
            var restDateString = dateService.convertDateToValidRestString(self.viewDate);

            daySchemeService.getDaySchemeByDate(restDateString).then(function (data) {
                self.availableVisitTimes = data.availableVisitTimes;
            }, function () {
                self.availableVisitTimes = undefined;
                console.log("Failed to load day scheme")
            })
        }

        function getNotConfirmedVisitTimes() {
            var dateString = dateService.convertDateToValidRestString(self.viewDate);

            daySchemeService.getNotConfirmedVisitTimes(dateString).then(function (data) {
                self.notConfirmedVisitTimes = data;
            }, function () {
                self.notConfirmedVisitTimes = undefined;
                console.log("Failed to load not confirmed visit times")
            })
        }

    });

})();