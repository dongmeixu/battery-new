(function () {
  'use strict';

  angular.module('BlurAdmin.pages.battery')
      .controller('generateCtrl', generateCtrl);

  /** @ngInject */
  function generateCtrl($scope, $http, toastr) {
    var getGenerateApi = 'http://localhost:3030/2dcodes';

    //默认值
    $scope.singGenerate = '';
    $scope.someGenerates = '';
    $scope.singleWar = true;
    $scope.someWar = true;
    $scope.single = true;
    $scope.some = true;


    

   
    $scope.singleMakeup = function(){
      if($scope.singleNum == null){
        alert("请输入流水号！");
       $scope.singleWar = true;
      }else{
     $http.get(getGenerateApi+'/'+ $scope.singleNum)
    .success(function(data,status,headers){
      $scope.singGenerate = data;
    }).error(function(data){
      toastr.error('获取数据失败', '', {});
          console.log("error: ", data);
    });
    $scope.singleWar = false;
    }
    },
    
    $scope.someMakeup = function(){
      if($scope.startNum == null || $scope.endNum == null || $scope.startNum >=$scope.endNum){
        alert("请输入正确的流水号范围！");
        $scope.someWar = true;
      }else{
    $http.get(getGenerateApi+"?"+"beginModuleId="+$scope.startNum+"&"+"endModuleId="+$scope.endNum)
    .success(function(data,status,headers){
      $scope.someGenerates = data;
    }).error(function(data){
      toastr.error('获取数据失败', '', {});
          console.log("error: ", data);
    });
    $scope.someWar = false;
  }
    },

    $scope.singleReload = function(){
    $scope.single = !$scope.single;
    $scope.some = true;
    $scope.startNum = null;
    $scope.endNum = null;
    },

    $scope.someReload = function(){
      $scope.single = true;
      $scope.some = !$scope.some;
      $scope.singleNum = null;

    };
  }

})();

