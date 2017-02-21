/**
 * @author zhaoxiaoyong
 * created on 2016.12.31
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.car', [])
    .config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('car', {
          url: '/car',
          template : '<ui-view></ui-view>',
          abstract: true,
          title: '汽车生产商',
          controller: 'carCtrl',
          sidebarMeta: {
            /*
            ** 一级菜单可以设置icon，二级菜单不可以
            ** icon素材来源：http://ionicons.com
            ** 找到合适的icon，填入其class名称
            */
            icon: 'ion-soup-can-outline',
            order: 150,
          },
        }).state('car.import', {
          url: '/import',
          templateUrl: 'app/pages/car/import/import.html',
          controller: 'ImportCtrl',
          title: '证书导入',
          sidebarMeta: {
            order: 151,
          }
        }).state('car.submit', {
          url: '/submit',
          templateUrl: 'app/pages/car/submit/submit.html',
          controller: 'SubmitCtrl',
          title: '信息上报',
          sidebarMeta: {
            order: 159,
          }
        }).state('car.trace', {
          url: '/carTrace',
          templateUrl: 'app/pages/car/carTrace/carTrace.html',
          controller: 'carTraceCtrl',
          title: '追溯查询',
          sidebarMeta: {
            order: 154,
          },
        })
        ;
    $urlRouterProvider.when('/car','/car/import');
  }

})();
