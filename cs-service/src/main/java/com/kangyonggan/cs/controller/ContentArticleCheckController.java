package com.kangyonggan.cs.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("content/articleCheck")
public class ContentArticleCheckController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 我的文章列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("articleCheck")
    public Response list() {
        Response response = Response.getSuccessResponse();
        PageInfo pageInfo = articleService.articles(getQuery());

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 审批
     *
     * @param type
     * @param replyMsg
     * @param ids
     * @return
     */
    @PutMapping
    @PermissionMenu("articleCheck")
    public Response check(@RequestParam("type") String type, @RequestParam("replyMsg") String replyMsg,
                          @RequestParam("ids") String ids) {
        articleService.articleReply(type, replyMsg, ids);
        return Response.getSuccessResponse();
    }
}
