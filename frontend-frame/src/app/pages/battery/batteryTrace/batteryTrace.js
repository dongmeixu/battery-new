(function () {
  'use strict';

  angular.module('BlurAdmin.pages.government')
  .controller('batteryTraceCtrl', batteryTraceCtrl);

  /** @ngInject */
  function batteryTraceCtrl($scope, $http, toastr, $filter) {

    var getTradesApi = "http://localhost:3003/trades";
    $scope.approveList = [];
    $scope.idList = [];
    $scope.array = [];
    $scope.limit = 5;
    $scope.offset = 0;
    $scope.Parameters = '';
    $scope.rowCount = 10;
    $scope.newDetailData =[];

    $scope.filters = {
      "startComp": "",
      "endComp": "",
      "startDate": "",
      "endDate": "",
      "startNum": "",
      "endNum": ""  
    };  


    
    //设置查询条件，只从后台中选出checked属性值为false的项
    $scope.newFilters = encodeURI('false,0');//这里填写api文档中的查询条件filters
    //设置查询条件中的limit&offset的值
    $scope.Parameters={
      "limit": $scope.limit,
      "offset": $scope.offset
    };
    //获取第一页的数据
    
    //api文档中还没写好，之后还需要更改
    $http.get(getTradesApi/*+'?' +'params=' + $scope.newFilters*/
              ,{params:$scope.Parameters}).success(function(data,headers){
      $scope.tracePageData =data;
      //$scope.rowCount = headers.X-Total-Count;
      $scope.pageSize = 5;
      $scope.selPage = 1;
      $scope.cutPage(); 
    }).error(function(data){
      toastr.error('数据获取失败', '', {});
          console.log("error: ", data);
    });

    //分页
    $scope.cutPage = function(){
      $scope.pages = Math.ceil($scope.rowCount / $scope.pageSize);//分页数
      console.log($scope.pages);
      $scope.newPages = $scope.pages >5?5:$scope.pages;
      $scope.pageList = [];
      for(var i=0;i<$scope.newPages;i++){
        $scope.pageList.push(i+1);
      }
    },

    //设置分页的表格数据源
    $scope.setData = function(){
    //通过当前页数筛选出表格当前显示数据
      $scope.offset = ($scope.selPage - 1) * $scope.limit;
      $scope.Parameters.offset = $scope.offset;
      //api文档中还没写好，之后还需要更改
      $http.get(getCertsApi+'?' +'params=' + $scope.newFilters
              ,{params:$scope.Parameters}).success(function(data){
        $scope.tracePageData =data;
        toastr.success('数据获取成功', '', {
           "timeOut": "1000",
           "closeButton": false,
          });
      }).error(function(data){
        toastr.error('数据获取失败', '', {});
          console.log("error: ", data);
      });
    },

    //打印当前选中页索引
    $scope.selectPage = function (page){
    //不能小于1大于最大
      if(page<1 || page>$scope.pages)return;
    //最多显示分页数5
      if(page>2){
    //因为只显示5个页数，大于2页就开始分页转换
      var newpageList = [];
      for(var i=(page - 3);i<((page + 2)>$scope.pages?$scope.pages:(page+2));i++){
      newpageList.push(i + 1);
      }
      $scope.pageList = newpageList;
      }
      $scope.selPage = page;
      $scope.setData();
      $scope.isActivePage(page);
      $scope.select_all = false;
      console.log("选择的页:" + page);
    },

    //设置当前选中页样式
    $scope.isActivePage = function(page){
      return $scope.selPage == page;
    },
    
    //上一页
    $scope.Previous = function(){
      $scope.selectPage($scope.selPage-1);
    },
    
    //下一页
    $scope.Next = function(){
      $scope.selectPage($scope.selPage + 1);
    },

    $scope.checked = [];

    //查询
    $scope.search = function(){ 
      $scope.selectComp = '';
      //api文档中还没写好，之后还需要更改
      $scope.selectComp = "\'" + $scope.filters.startComp + "\'" +","+ "\'" + $scope.filters.endComp + "\'" + ","
                           + $filter('date')($scope.filters.startDate,'yyyy-MM-dd') + "\'"
                           +","+ "\'" + $filter('date')($scope.filters.endDate,'yyyy-MM-dd') + "\'" +","
                           + "\'" + $scope.filters.startNum + "\'"+ ","+"\'" + $scope.filters.endNum + "\'";
      $scope.newParams = encodeURI($scope.selectComp);
      console.log($scope.newParams);
      $http.get(getTradesApi+ '?filters='+ $scope.newFilters+'&params='+$scope.newParams,{params:$scope.Parameters}).success(function(data){
        $scope.tracePageData = data; 
      }).error(function(){
        toastr.error('查询数据失败', '', {});
          console.log("error: ", data);
      });
    },

    $scope.checkDetail = function(item){
      $scope.newDetailData =[];
      $scope.productId = item.id;
      $scope.modelId = item.creditCode;
      $http.get(getTradesApi+"/"+item.id).success(function(data){
            $scope.detailData = data;
            $scope.newDetailData = $scope.detailData;
      }).error(function(){
        toastr.error('查询数据失败', '', {});
          console.log("error: ", data);
      });


    };



}

})();

