'use strict';

angular.module('wevver', [])
    .controller('IndexController', ['$scope', '$http', '$interval', function ($scope, $http, $interval) {

        function checkWeather() {
            $http({method: 'GET', url: '/api/wevvers/'}).
                success(function(data) {
                    var cities = [];
                    for (var city in data) {
                        cities.push(data[city]);
                    }
                    $scope.cities = cities;
                }).error(function(data) {
                    $scope.error = data;
                });
        }

        // Periodically check for updated wevver data
        $interval(checkWeather, 1500);

    }]);