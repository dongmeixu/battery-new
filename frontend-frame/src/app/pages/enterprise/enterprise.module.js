/**
 * @author zhaoxiaoyong
 * created on 2016.12.31
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.enterprise', [])
    .config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('enterprise', {
          url: '/enterprise',
          template : '<ui-view></ui-view>',
          abstract: true,
          title: '企业用户',
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
        }).state('enterprise.doc', {
          url: '/doc',
          templateUrl: 'app/pages/enterprise/doc/doc.html',
          controller: 'docCtrl',
          title: '帮助',
          sidebarMeta: {
            order: 160,
          }
        }).state('enterprise.myApply', {
          url: '/myApply',
          templateUrl: 'app/pages/enterprise/myApply/myApply.html',
          controller: 'myApplyCtrl',
          title: '我的申请',
          sidebarMeta: {
            order: 159,
          }
        })
        
        
        ;
    $urlRouterProvider.when('/enterprise','/enterprise/import');
  }

})();
