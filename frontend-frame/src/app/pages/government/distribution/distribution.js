(function () {
  'use strict';

  angular.module('BlurAdmin.pages.government')
  .controller('DistributionCtrl', DistributionCtrl);

  /** @ngInject */
  function DistributionCtrl($scope, $http, toastr, $filter) {
    var getDensitysApi = "http://localhost:3003/densitys";
    $scope.distributionData = [];
    $scope.filters={
        "year": "",
        "companyName": ""
    };
    $scope.newfilters = encodeURI('{year:#,companyName:#}');
    console.log($scope.newfilters);
    
    $scope.showTable = function(){


      $scope.selectComp = '';
      $scope.selectComp ="\'"+ $scope.filters.year + "\'"+","
                         + "'.*"+ $scope.filters.companyName + ".*'";
      $scope.newParams = encodeURIComponent($scope.selectComp);
      console.log($scope.newParams);
      $http.get(getCertsApi+ '?filters='+$scope.newfilters +'&params=' + $scope.newParams).success(function(data){
        $scope.distributionData = data; 
      });
    };

    $scope.randomData = function () {
    return Math.round(Math.random()*1000);
}



$scope.mapConfig = {
                                theme:'default',
                                
                                dataLoaded:true
        };


var geoCoordMap = {
    '比克电池':[114.437302,22.617655],
            '比亚迪汽车':[114.433617,22.632961],
            '奇瑞汽车':[118.432941,31.352859],
            '众泰汽车':[113.957100,22.580023],
            '福特汽车':[121.553993,31.126375],
            '乾泰':[114.293350,22.779459],
            '一汽':[125.226006,43.846668],
            '东风日产':[113.168143,23.379142],
            '宇通客车':[113.867538,34.692932]
};



$scope.mapOption = {
    title: {
        text: '电池模组密度分布',
        subtext: '',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data:['电池模组密度分布']
    },
    visualMap: {
        min: 0,
        max: 2500,
        left: 'left',
        top: 'bottom',
        text: ['高','低'],           // 文本，默认为数值文本
        calculable: true
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name: '电池模组密度分布',
            type: 'map',
            mapType: 'china',
            roam: false,
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            data://$scope.distributionData
            [
                {name: '北京',value: $scope.randomData() },
                {name: '天津',value: $scope.randomData() },
                {name: '上海',value: $scope.randomData() },
                {name: '重庆',value: $scope.randomData() },
                {name: '河北',value: $scope.randomData() },
                {name: '河南',value: $scope.randomData() },
                {name: '云南',value: $scope.randomData() },
                {name: '辽宁',value: $scope.randomData() },
                {name: '黑龙江',value: $scope.randomData() },
                {name: '湖南',value: $scope.randomData() },
                {name: '安徽',value: $scope.randomData() },
                {name: '山东',value: $scope.randomData() },
                {name: '新疆',value: $scope.randomData() },
                {name: '江苏',value: $scope.randomData() },
                {name: '浙江',value: $scope.randomData() },
                {name: '江西',value: $scope.randomData() },
                {name: '湖北',value: $scope.randomData() },
                {name: '广西',value: $scope.randomData() },
                {name: '甘肃',value: $scope.randomData() },
                {name: '山西',value: $scope.randomData() },
                {name: '内蒙古',value: $scope.randomData() },
                {name: '陕西',value: $scope.randomData() },
                {name: '吉林',value: $scope.randomData() },
                {name: '福建',value: $scope.randomData() },
                {name: '贵州',value: $scope.randomData() },
                {name: '广东',value: $scope.randomData() },
                {name: '青海',value: $scope.randomData() },
                {name: '西藏',value: $scope.randomData() },
                {name: '四川',value: $scope.randomData() },
                {name: '宁夏',value: $scope.randomData() },
                {name: '海南',value: $scope.randomData() },
                {name: '台湾',value: $scope.randomData() },
                {name: '香港',value: $scope.randomData() },
                {name: '澳门',value: $scope.randomData() }
            ]
        }/*,
        {
            name: 'iphone4',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            data:[
                {name: '北京',value: $scope.randomData() },
                {name: '天津',value: $scope.randomData() },
                {name: '上海',value: $scope.randomData() },
                {name: '重庆',value: $scope.randomData() },
                {name: '河北',value: $scope.randomData() },
                {name: '安徽',value: $scope.randomData() },
                {name: '新疆',value: $scope.randomData() },
                {name: '浙江',value: $scope.randomData() },
                {name: '江西',value: $scope.randomData() },
                {name: '山西',value: $scope.randomData() },
                {name: '内蒙古',value: $scope.randomData() },
                {name: '吉林',value: $scope.randomData() },
                {name: '福建',value: $scope.randomData() },
                {name: '广东',value: $scope.randomData() },
                {name: '西藏',value: $scope.randomData() },
                {name: '四川',value: $scope.randomData() },
                {name: '宁夏',value: $scope.randomData() },
                {name: '香港',value: $scope.randomData() },
                {name: '澳门',value: $scope.randomData() }
            ]
        },
        {
            name: 'iphone5',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            data:[
                {name: '北京',value: $scope.randomData() },
                {name: '天津',value: $scope.randomData() },
                {name: '上海',value: $scope.randomData() },
                {name: '广东',value: $scope.randomData() },
                {name: '台湾',value: $scope.randomData() },
                {name: '香港',value: $scope.randomData() },
                {name: '澳门',value: $scope.randomData() }
            ]
        }*/
    ]
};



    }

})();
