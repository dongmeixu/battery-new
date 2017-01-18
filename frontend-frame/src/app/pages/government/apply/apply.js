(function () {
  'use strict';

  angular.module('BlurAdmin.pages.government')
      .controller('ApplyCtrl', ApplyCtrl);

  /** @ngInject */
  function ApplyCtrl($scope, $http, toastr) {
    var applyApi = 'http://localhost:3030/certs';


    //默认值
    $scope.applyData = {
      'companyType': '',
      'companyName': '',
      'creditCode': '',
      'factoryCode': '',
      'contact': '',
      'phone': '',
      'email': '',
      'certFile': ''
    };

    //确认录入
    $scope.confirm = function() {
      $http.post(applyApi, $scope.applyData)
        .success(function(data){
          toastr.success('录入成功', '', {
            "timeOut": "1000",
            "closeButton": false
          });
        })
        .error(function(data){
          toastr.error('录入失败', '', {});
          console.log("error: ", data);
        });
    }



    //文件上传例子
    $scope.uploadFile = function(elem) {
        if (window.File && window.FileReader && window.FormData) {
            var file = elem.files[0];
            var notValidate=true;
            if (file) {
                console.log(file.type);
                if (/jpg/i.test(file.type) || notValidate) {
                    imagePreview(file);
                } else {
                    alert('Not a valid jpg file!');
                }
            }
        } else {
            alert("File upload is not supported!");
        }
    }

    //文件预览
      $scope.preview="";
      function imagePreview(file) {
          var reader = new FileReader();
              reader.onloadend = function() {
                  $scope.preview = reader.result;
                  $scope.applyData.certFile=reader.result;
              };
              reader.readAsDataURL(file);
          }

  }
})();

