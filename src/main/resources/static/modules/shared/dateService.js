(function () {
    angular.module("sharedServices").service("dateService",function() {
        var self=this;
        self.polishDayNames=["Niedziela","Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota"];
        self.polishMonthName=["Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień",
            "Październik","Listopad","Grudzień"];

        self.convertStringToDate=function (dateString) {
            var parts=dateString.split("-");
            var date=new Date();

            date.setDate(parts[0]);
            date.setMonth(parts[1]-1);
            date.setYear(parts[2]);

            return date;
        };

        self.changeDateByGivenNumberOfDays=function (dateToChange,number) {
            var date=new Date();

            date.setFullYear(dateToChange.getFullYear());
            date.setMonth(dateToChange.getMonth());
            date.setDate(dateToChange.getDate()+number);

            return date
        };

        self.convertDateToValidRestString=function (date) {
            var days=date.getDate();
            var month=date.getMonth()+1;

            if(days<10)
                days="0"+days;
            if(month<10)
                month="0"+month;

            return days+"-"+month+"-"+date.getFullYear();
        };

        self.convertDateToPolishString=function(date){
            return self.polishDayNames[date.getDay()]+", "+date.getDate()+" "+self.polishMonthName[date.getMonth()]
                +", "+date.getFullYear();
        };

        self.convertDateStringToPolishString = function (dateString) {
            var date = self.convertStringToDate(dateString);
            return self.convertDateToPolishString(date);
        };

    });

})();