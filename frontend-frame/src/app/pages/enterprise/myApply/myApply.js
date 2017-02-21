(function () {
    'use strict';

    angular.module('BlurAdmin.pages.enterprise')
        .controller('myApplyCtrl', myApplyCtrl);

    /** @ngInject */
    function myApplyCtrl($scope, $http, toastr) {
        var applyApi = 'http://localhost:3030/v1/company/';

        //默认值
        $scope.show = "";
        $scope.status = 0;
        $scope.id = "";

        switch ($scope.status) {
            case 0:
                $scope.show = "未审核";
                break;
            case 1:
                $scope.show = "审核通过";
                break;
            case 2:
                $scope.show = "审核不通过";
                break;
        }


        //确认录入
        $http.get(applyApi + $scope.id + '/' + 'certs')
            .success(function (data) {
                $scope.status = data.status;
            })
            .error(function (data) {
                toastr.error('失败', '', {});
                console.log("error: ", data);
            });

    }
})();

