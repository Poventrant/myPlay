/*
 * *
 *  *
 *  * 功能描述：
 *  * <p> 版权所有：优视科技
 *  * <p> 未经本公司许可，不得以任何方式复制或使用本程序任何部分 <p>
 *  *
 *  * @author <a href="wb-pwq174842@alibaba-inc.com">庞文全</a>
 *  * @version 1.0.1
 *  * create on: 2016年
 *
 */
'use strict';

angular.module("ar_admin").controller('pixivAddCtrl',
	function($scope, $http, UtilService, PixivResource, SysParamService) {
		var errHandler = function() {
			UtilService.alertError('提示', '链接失败');
		};
		var sucHandler = function() {
			UtilService.alertSuccess('提示', '下载添加成功');
		};

		PixivResource.getAuthors({}).$promise.then(function(res) {
			$scope.authors = res.authors;
			$scope.author = $scope.authors[0];
		})

		$scope.doAdd = function() {
			PixivResource.put({
				authorId: $scope.authorId
			}).$promise.then(function(res) {
				UtilService.alert('提示', res.msg);
			})
		}

		$scope.doDelete = function() {
			if(!confirm("你确定要删除吗？")) return false;
			PixivResource.deleteByAuthor({
				author: $scope.author
			}).$promise.then(function(res) {
				if(res.success) {
					window.location.reload();
				} else {
					UtilService.alert('提示', res.msg);
				}
			})
		}

		$scope.doAllDelete = function() {
			if(!confirm("你确定要全部删除吗？")) return false;
			PixivResource.deleteAll({
			}).$promise.then(function(res) {
				if(res.success) {
					window.location.reload();
				} else {
					UtilService.alert('提示', res.msg);
				}
			})
		}

		$scope.resetPixivLoginCookie = function() {
			$scope.overLay = true;
		}

		$scope.closeOverLay = function() {
			$scope.overLay = false;
		}

		$scope.pixiv = {};
		$scope.setPixivCookie = function() {
			PixivResource.setPixivCookie($scope.pixiv)
			.$promise.then(function(res) {
				if(res.success) {
					$scope.overLay = false;
					UtilService.alert('提示', res.msg);
				} else {
					UtilService.alert('提示', res.msg);
				}
			})
		}

	})