package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Pixiv;
import play.Logger;
import play.mvc.Controller;
import services.PixivService;
import utils.PixivUtil;

import javax.inject.Inject;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PixivController extends Controller {

    @Inject
    private static PixivService pixivService;

    public static void queryAll(){
        try{
            if(pixivService == null){
                Logger.error("the pixivService is null");
            }
            BaseParamVO vo = (BaseParamVO) formatData(BaseParamVO.class);
            Map<String, Object> map = pixivService.queryAll(vo);
            Gson gb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            renderJSON(gb.toJson(map));
        }catch(Exception e){
            Logger.error("query all error", e, params);
        }
    }

    public static void find(int id){
        Pixiv tmpDO = pixivService.find( id);
        if( tmpDO == null){
            renderJSON("{}");
        }
        renderJSON( tmpDO);
    }
    public static void saveOrUpdate() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            String authorId = params.get("authorId");
            if(authorId == null || authorId.equals("")) {
                Logger.error("作者ID不能为空");
                throw new Exception("作者ID不能为空.");
            }
            PixivUtil putil = new PixivUtil(authorId, pixivService);
            putil.doGetByAuthorAync();
            returnMap.put("msg", "正在下载中，请等待...");
            renderJSON(returnMap);
        } catch (Exception e) {
            Logger.error("更新数据库信息错误!", e, params);
            returnMap.put("msg", e.getMessage());
            renderJSON(returnMap);
        }
       /* List<String> list = PixivUtil.getAuthorIdList();
        for(String authorId: list){
            PixivUtil putil = new PixivUtil(authorId, pixivService);
            putil.getByAuthorAync();
        }
        renderJSON(returnMap);*/
    }
    public static void delete(int idstr){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
            if(pixivService.delete( idstr, getLoginUser() ) != 0 ){
                returnMap.put("success", true);
            }
            renderJSON(returnMap);
        } catch (Exception e) {
            Logger.error("删除错误", e, params);
            returnMap.put("msg", e.getMessage());
            renderJSON(returnMap);
        }
    }

    public static void deleteByAuthor(String author){
        System.out.println(author);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
            pixivService.deleteByAuthor(author);
            returnMap.put("msg", "删除成功~");
            returnMap.put("success", true);
            renderJSON(returnMap);
        } catch (Exception e) {
            Logger.error("删除错误", e, params);
            returnMap.put("msg", e.getMessage());
            returnMap.put("success", false);
            renderJSON(returnMap);
        }
    }

    public static void deleteAll() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        File file = new File(PixivUtil.DOWNLOAD_PATH);
        File[] files = file.listFiles();
        if(files.length == 0) {
            returnMap.put("msg", "不存在。");
            returnMap.put("success", false);
            renderJSON(returnMap);
        }
        for(File f : files) {
            if(f.isDirectory()) {
                try {
                    PixivUtil.deleteAuthor(f.getName());
                } catch (Exception e) {
                    Logger.error("删除错误, 请重试~", e, params);
                    returnMap.put("msg", e.getMessage());
                    returnMap.put("success", false);
                    renderJSON(returnMap);
                }
            }
        }
        try {
            pixivService.deleteAll();
        } catch (Exception e) {
            Logger.error("删除错误, 请重试~", e, params);
            returnMap.put("msg", e.getMessage());
            returnMap.put("success", false);
            renderJSON(returnMap);
        }
        returnMap.put("msg", "删除成功~");
        returnMap.put("success", true);
        renderJSON(returnMap);
    }

    public static void queryByPage(){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
            String paramsStr = params.get("querysModel");

            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> paramsMap = new Gson().fromJson(paramsStr, type);

            List<Pixiv> arpList = pixivService.queryByPage(paramsMap, returnMap);
            returnMap.put("authors", pixivService.getAuthors(true));
            returnMap.put("pixivList", arpList);
            returnMap.put("success", true);
            returnMap.put("msg", "成功");
            renderJSON(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("success", false);
            returnMap.put("msg", e.getMessage());
            renderJSON(returnMap);
        }
    }

    public static void getAuthors() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
            returnMap.put("msg", "成功");
            returnMap.put("authors", pixivService.getAuthors(false));
            renderJSON(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("msg", e.getMessage());
            renderJSON(returnMap);
        }
    }

    public static void setPixivCookie() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String sessid = params.get("sessid");
        String token = params.get("token");
        String target = "PHPSESSID=" + sessid + ";" + "device_token=" + token + ";";
        PixivUtil.saveLoginInfo(target);
        returnMap.put("msg", "成功");
        returnMap.put("success", true);
        renderJSON(returnMap);
    }

}
