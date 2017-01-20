/**
 * @author zhaoxiaoyong
 * created on 2016.12.31
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.battery', [])
    .config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('battery', {
          url: '/battery',
          template : '<ui-view></ui-view>',
          abstract: true,
          title: '电池生产商',
          controller: 'governmentCtrl',
          sidebarMeta: {
            /*
            ** 一级菜单可以设置icon，二级菜单不可以
            ** icon素材来源：http://ionicons.com
            ** 找到合适的icon，填入其class名称
            */
            icon: 'ion-soup-can-outline',
            order: 150,
          },
        }).state('battery.import', {
          url: '/import',
          templateUrl: 'app/pages/battery/import/import.html',
          controller: 'importCtrl',
          title: '证书导入',
          sidebarMeta: {
            order: 151,
          }
        }).state('battery.submit', {
          url: '/submit',
          templateUrl: 'app/pages/battery/submit/submit.html',
          controller: 'submitCtrl',
          title: '信息上报',
          sidebarMeta: {
            order: 159,
          }
        })
        .state('battery.generate', {
          url: '/generate',
          templateUrl: 'app/pages/battery/generate/generate.html',
          controller: 'generateCtrl',
          title: '生成二维码',
          sidebarMeta: {
            order: 160,
          }
        })
        .state('battery.trace', {
          url: '/batteryTrace',
          templateUrl: 'app/pages/battery/batteryTrace/batteryTrace.html',
          controller: 'batteryTraceCtrl',
          title: '追溯查询',
          sidebarMeta: {
            order: 154,
          },
        })
        ;
    $urlRouterProvider.when('/battery','/battery/import');
  }

})();
