var app = angular.module('app', []);

app.controller('thingCtrl', ['$scope', 'thingService', function ($scope, thingService) {
    thingService.getAllThings().then(function (response) {
        $scope.things = response.data;
    }, function (response) {
        $scope.error = response.data;
    });
}]);

app.service('thingService', ['$http', function ($http) {
    this.getAllThings = function getAllThings() {
        return $http.get('/api/v1/things');
    };
}]);