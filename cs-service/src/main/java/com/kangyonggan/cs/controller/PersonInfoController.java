package com.kangyonggan.cs.controller;

import com.kangyonggan.app.util.FileUpload;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.model.User;
import com.kangyonggan.cs.service.UserService;
import com.kangyonggan.cs.util.AuthUtil;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@RestController
@RequestMapping("person/info")
public class PersonInfoController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${file.root.path}")
    private String fileRootPath;

    /**
     * 更新基本信息
     *
     * @param user
     * @return
     */
    @PutMapping
    @PermissionMenu("info")
    public Response updateInfo(User user) {
        Response response = Response.getSuccessResponse();
        user.setId(AuthUtil.currentUserId());
        user.setUsername(null);
        userService.updateUser(user);

        user = userService.findUserById(user.getId());
        response.put("user", user);
        return response;
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     * @throws FileUploadException
     */
    @PutMapping("avatar")
    @PermissionMenu("info")
    public Response avatar(@RequestParam("file") MultipartFile file) throws FileUploadException {
        Response response = Response.getSuccessResponse();

        if (file != null && !file.isEmpty()) {
            String avatarPath = FileUpload.upload(fileRootPath, "upload/", file, "AVATAR");
            User user = new User();
            user.setId(AuthUtil.currentUserId());
            user.setAvatar(avatarPath);
            userService.updateUser(user);

            response.put("user", userService.findUserById(user.getId()));
        } else {
            response.failure();
        }

        return response;
    }

}
