(function () {
  'use strict';

  angular.module('BlurAdmin.pages.government')
  .controller('DistributionCtrl', DistributionCtrl);

  /** @ngInject */
  function DistributionCtrl($scope, $http, toastr, $filter) {
    var getDensitysApi = "http://localhost:3003/densitys"
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
      $scope.newParams = encodeURI($scope.selectComp);
      console.log($scope.newParams);
      $http.get(getCertsApi+ '?filters='+$scope.newfilters +'&params=' + $scope.newParams).success(function(data){
        $scope.distributionData = data; 
      });
    };



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

var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};

$scope.mapOption = {
    backgroundColor: '#404a59',
    title: {
        text: '厂家电池密度分布',
        subtext: '',
        sublink: '',
        x:'center',
        textStyle: {
            color: '#fff'
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
            return params.name + ' : ' + params.value[2];
        }
    },
    legend: {
        orient: 'vertical',
        y: 'bottom',
        x:'right',
        data:['密度分布'],
        textStyle: {
            color: '#fff'
        }
    },
    visualMap: {
        min: 0,
        max: 200,
        calculable: true,
        inRange: {
            color: ['#50a3ba', '#eac736', '#d94e5d']
        },
        textStyle: {
            color: '#fff'
        }
    },
    geo: {
        map: 'china',
        label: {
            emphasis: {
                show: false
            }
        },
        itemStyle: {
            normal: {
                areaColor: '#323c48',
                borderColor: '#111'
            },
            emphasis: {
                areaColor: '#2a333d'
            }
        }
    },
    series: [
        {
            name: '密度分布',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: convertData( //以后将这里改为$scope.distributionData
                [
                {name: "比克电池", value: 9},
                {name: "比亚迪汽车", value: 120},
                {name: "奇瑞汽车", value: 90},
                {name: "众泰汽车", value: 40},
                {name: "福特汽车", value: 14},
                {name: "乾泰", value: 50},
                {name: "一汽", value: 16},
                {name: "东风日产", value: 18},
                {name: "宇通客车", value: 18}
            ]),
            symbolSize: 12,
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            itemStyle: {
                emphasis: {
                    borderColor: '#fff',
                    borderWidth: 1
                }
            }
        }
    ]
}



    }

})();
