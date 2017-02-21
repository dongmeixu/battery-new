/**
 * Created by imacox on 17/1/17.
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.car')
        .controller('ImportCtrl', ImportCtrl);

    /** @ngInject */
    function ImportCtrl($scope, $http, toastr) {

        //文件上传
        $scope.uploadFile = function(elem) {
            if (window.File && window.FileReader && window.FormData) {
                var file = elem.files[0];
                var notValidate=true;
                if (file) {
                    console.log(file.type);
                    if (/zip/i.test(file.type) || notValidate) {
                        readFile(file, sendFile);
                    } else {
                        alert('Not a valid zip file!');
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


