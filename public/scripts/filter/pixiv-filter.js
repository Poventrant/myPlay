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

angular.module("ar_admin").filter("mutipleFilter", function() {
    return function(str) {
        var strs = str.split("/");
        var len = strs.length;
        var groups = /([^_]*)[^.]*[\.](.*)/.exec(strs[len-1]);
        return strs[len-2] + "/" + groups[1] + "/" + groups[2];
    }
})
