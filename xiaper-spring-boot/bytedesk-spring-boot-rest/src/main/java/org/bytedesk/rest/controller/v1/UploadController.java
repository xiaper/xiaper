package org.bytedesk.rest.controller.v1;

import org.bytedesk.jpa.util.JpaUtil;
import org.bytedesk.jpa.util.JsonResult;
import org.bytedesk.jpa.util.LayIMUploadJsonResult;
import org.bytedesk.rest.util.AliYunOss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/visitor/api/upload")
public class UploadController {

    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    AliYunOss aliYunOss;

    enum UPLOAD_TYPE {
        AVATAR, IMAGE, FILE, WECHATDB
    }

    /**
     * 上传头像
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/avatar")
    public JsonResult uploadAvatar(@RequestParam("file_name") String fileName,
                                   @RequestParam("username") String username,
                                   @RequestParam("file") MultipartFile multipartFile) {

        return uploadCommon(fileName, username, multipartFile, UPLOAD_TYPE.AVATAR);
    }

    /**
     * 上传图片
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/image")
    public JsonResult uploadImage(@RequestParam("file_name") String fileName,
                                  @RequestParam("username") String username,
                                  @RequestParam("file") MultipartFile multipartFile) {

        return uploadCommon(fileName, username, multipartFile, UPLOAD_TYPE.IMAGE);
    }


    /**
     * 上传文件
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/file")
    public JsonResult uploadFile(@RequestParam("file_name") String fileName,
                                 @RequestParam("username") String username,
                                 @RequestParam("file") MultipartFile multipartFile) {

        return uploadCommon(fileName, username, multipartFile, UPLOAD_TYPE.FILE);
    }


    /**
     * 上传ios pem文件
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/pem")
    public JsonResult uploadPem(@RequestParam("file_name") String fileName,
                                 @RequestParam("username") String username,
                                 @RequestParam("file") MultipartFile multipartFile) {

        return uploadCommon(fileName, username, multipartFile, UPLOAD_TYPE.FILE);
    }



    /**
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/wechatdb")
    public JsonResult uploadWeChatDb(@RequestParam("file_name") String fileName,
                                 @RequestParam("username") String username,
                                 @RequestParam("file") MultipartFile multipartFile) {

        return uploadCommon(fileName, username, multipartFile, UPLOAD_TYPE.WECHATDB);
    }


    /**
     * 公众号开发文档：
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
     *
     * weixin-java-tools 开发文档：
     * https://github.com/Wechat-Group/weixin-java-tools/blob/master/weixin-java-mp/src/main/java/me/chanjar/weixin/mp/api/impl/WxMpMaterialServiceImpl.java
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @return
     */
    @PostMapping("/wechat/image")
    public JsonResult uploadWeChatImage(@RequestParam("file_name") String fileName,
                                        @RequestParam("username") String username,
                                        @RequestParam("file") MultipartFile multipartFile) {

        JsonResult jsonResult = new JsonResult();



        return jsonResult;
    }

    @PostMapping("/wechat/voice")
    public JsonResult uploadWeChatVoice(@RequestParam("file_name") String fileName,
                                        @RequestParam("username") String username,
                                        @RequestParam("file") MultipartFile multipartFile) {

        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }

    @PostMapping("/wechat/video")
    public JsonResult uploadWeChatVideo(@RequestParam("file_name") String fileName,
                                        @RequestParam("username") String username,
                                        @RequestParam("file") MultipartFile multipartFile) {

        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }

    @PostMapping("/wechat/thumb")
    public JsonResult uploadWeChatThumb(@RequestParam("file_name") String fileName,
                                        @RequestParam("username") String username,
                                        @RequestParam("file") MultipartFile multipartFile) {

        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }


    /**
     * Layim 上传图片
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/layim/image")
    public LayIMUploadJsonResult uploadLayIMImage(@RequestParam("file") MultipartFile multipartFile) {

        return uploadLayIMCommon(multipartFile, UPLOAD_TYPE.IMAGE);
    }


    /**
     * Layim 上传文件
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/layim/file")
    public LayIMUploadJsonResult uploadLayIMFile(@RequestParam("file") MultipartFile multipartFile) {

        return uploadLayIMCommon(multipartFile, UPLOAD_TYPE.FILE);
    }


    /**
     * 上传
     * TODO: 上传完毕后，清理掉本地缓存
     *
     * @param fileName
     * @param username
     * @param multipartFile
     * @param type
     * @return
     */
    private JsonResult uploadCommon(String fileName, String username, MultipartFile multipartFile, UPLOAD_TYPE type) {

        logger.info("filename:" + fileName + " username:" + username);

        JsonResult jsonResult = new JsonResult();

        if (type.equals(UPLOAD_TYPE.AVATAR)) {
            jsonResult.setMessage("upload avatar");
        } else if (type.equals(UPLOAD_TYPE.IMAGE)) {
            jsonResult.setMessage("upload image");
        } else if (type.equals(UPLOAD_TYPE.FILE)) {
            jsonResult.setMessage("upload file");
        } else if (type.equals(UPLOAD_TYPE.WECHATDB)) {
            jsonResult.setMessage("upload wechat db");
        }

        try {

            if(!multipartFile.isEmpty()){

                String filename = multipartFile.getOriginalFilename();
                if(!"".equals(filename.trim())) {

                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(multipartFile.getBytes());
                    os.close();
                    multipartFile.transferTo(newFile);

                    //上传到OSS
                    if (type.equals(UPLOAD_TYPE.AVATAR)) {
                        String uploadUrl = aliYunOss.uploadAvatar(fileName,newFile);
                        jsonResult.setData(uploadUrl);
                    } else if (type.equals(UPLOAD_TYPE.FILE)) {
                        String uploadUrl = aliYunOss.uploadFile(fileName,newFile);
                        jsonResult.setData(uploadUrl);
                    } else if (type.equals(UPLOAD_TYPE.WECHATDB)) {
                        String uploadUrl = aliYunOss.uploadWeChatDb(fileName,newFile);
                        jsonResult.setData(uploadUrl);
                    } else if (type.equals(UPLOAD_TYPE.IMAGE)) {
                        String uploadUrl = aliYunOss.uploadImage(fileName,newFile);
                        jsonResult.setData(uploadUrl);
                    }
                    // 删除文件
                    newFile.delete();
                    //
                    jsonResult.setStatus_code(200);
                }
                else {

                    logger.error("fileName is null");
                    jsonResult.setStatus_code(-2);
                }
            }
            else {

                logger.error("multipart file is null");
                jsonResult.setStatus_code(-1);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return jsonResult;
    }


    /**
     * layim 上传图片格式
     *
     * @param multipartFile
     * @param type
     * @return
     */
    public LayIMUploadJsonResult uploadLayIMCommon(MultipartFile multipartFile, UPLOAD_TYPE type) {

        LayIMUploadJsonResult jsonResult = new LayIMUploadJsonResult();

        String fileName = JpaUtil.uuid();

        try {

            if(!multipartFile.isEmpty()){

                String filename = multipartFile.getOriginalFilename();
                if(!"".equals(filename.trim())) {

                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(multipartFile.getBytes());
                    os.close();
                    multipartFile.transferTo(newFile);

                    //上传到OSS
                    if (type.equals(UPLOAD_TYPE.FILE)) {

                        String uploadUrl = aliYunOss.uploadFile(fileName,newFile);

                        Map<String, Object> objectMap = new HashMap<>();
                        objectMap.put("src", uploadUrl);
                        objectMap.put("name", fileName);

                        jsonResult.setData(objectMap);

                    } else if (type.equals(UPLOAD_TYPE.IMAGE)) {

                        String uploadUrl = aliYunOss.uploadImage(fileName,newFile);

                        Map<String, Object> objectMap = new HashMap<>();
                        objectMap.put("src", uploadUrl);

                        jsonResult.setData(objectMap);
                    }
                    // 删除文件
                    newFile.delete();
                    //
                    jsonResult.setCode(0);
                }
                else {
                    jsonResult.setMsg("fileName is null");
                    jsonResult.setCode(-2);
                }
            }
            else {
                jsonResult.setMsg("multipart file is null");
                jsonResult.setCode(-1);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return jsonResult;
    }



}
