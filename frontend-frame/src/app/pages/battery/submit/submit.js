(function () {
    'use strict';

    angular.module('BlurAdmin.pages.battery')
        .controller('SubmitCtrl', SubmitCtrl);

    /** @ngInject */
    function SubmitCtrl($scope, $http, toastr) {
        var packageApi = "http://localhost:9000/v1/packages"

        /* API 和原型不一致 需要讨论确定字段*/
        $scope.option = "";
        $scope.package = {
            packageId: "",
            packageSpec: "",
            modules: []
        };

        $scope.confirm = function () {
            $http.post(packageApi, $scope.package)
                .success(function (data) {
                    toastr.success('录入成功', '', {
                        "timeOut": "1000",
                        "closeButton": false
                    });
                })
                .error(function (data) {
                    toastr.error('录入失败', '', {});
                    console.log("error: ", data);
                });
        }

        //文件上传
        $scope.uploadFile = function (elem) {
            if (window.File && window.FileReader && window.FormData) {
                var file = elem.files[0];
                var notValidate = true;
                if (file) {
                    console.log(file.type);
                    if (/xls/i.test(file.type) || notValidate) {
                        readFile(file, sendFile);
                    } else {
                        alert('Not a valid xls file!');
                    }
                }
            } else {
                alert("File upload is not supported!");
            }
        }

        //readFile
        function readFile(file, callback) {
            var reader = new FileReader();
            reader.onloadend = function () {
                callback(reader.result);
            }
            reader.onerror = function () {
                alert('There was an error reading the file!');
            }
            reader.readAsDataURL(file);
        }

        //send file
        function sendFile(fileData) {
            var formData = new FormData();

            formData.append('attachment', fileData);

            $http({
                method: 'POST',
                url: 'http://localhost:9000/v1/attachments',
                transformRequest: angular.identity,
                headers: {'Content-Type': false},
                data: formData
            }).success(function (data) {
                console.log('success: ', data);
            });
        }


    }

})();

