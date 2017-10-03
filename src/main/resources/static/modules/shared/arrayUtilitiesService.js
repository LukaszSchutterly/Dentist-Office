(function () {
    angular.module("sharedServices").service("arrayUtilitiesService",function () {
        var self=this;

        self.removeElementFromArray=function (array,element) {
            var index=array.indexOf(element);
            array.splice(index,1);
        };

        self.replaceElementInArray=function (array,arrayElement,newElement) {
            var index=array.indexOf(arrayElement);
            array[index]=newElement;
        }

    })

})();