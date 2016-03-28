/*
 * *
 *  *
 *  * 功能描述：
 *  * <p> 版权所有：优视科技
 *  * <p> 未经本公司许可，不得以任何方式复制或使用本程序任何部分 <p>
 *  *
 *  * @author <a href="296306654@qq.com">庞文全</a>
 *  * @version 1.0.1
 *  * create on: 2016年
 *
 */

'use strict';

angular.module("ar_admin").controller('pixivOriginCtrl',
	function($scope, $http, $routeParams, PixivResource, SysParamService) {
        var authorId = $routeParams.authorId,
            picid = $routeParams.picid,
            type = $routeParams.type;
        const picno = parseInt( $routeParams.picno );

        $scope.pixiv = {};
        $scope.pixiv.authorId = authorId;
        $scope.pixiv.picid = picid;
        $scope.pixiv.type = type;
        $scope.pixiv.picno = new Array(picno);
	})