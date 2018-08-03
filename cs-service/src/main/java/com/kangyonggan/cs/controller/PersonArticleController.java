package com.kangyonggan.cs.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.ap.model.Article;
import com.kangyonggan.app.util.MarkdownUtil;
import com.kangyonggan.common.Query;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.annotation.PermissionUser;
import com.kangyonggan.cs.model.User;
import com.kangyonggan.cs.service.ArticleService;
import com.kangyonggan.cs.service.UserService;
import com.kangyonggan.cs.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@RestController
@RequestMapping("person/article")
public class PersonArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 我的文章列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("article")
    public Response list() {
        Response response = Response.getSuccessResponse();
        Query query = getQuery();
        query.put("createdUsername", AuthUtil.currentUsername());

        PageInfo pageInfo = articleService.articles(query);
        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 保存
     *
     * @param article
     * @return
     */
    @PostMapping
    @PermissionMenu("article")
    public Response save(Article article) {
        User user = userService.findUserByUsername(AuthUtil.currentUsername());
        article.setCreatedUsername(user.getUsername());
        article.setCreatedName(user.getName());
        articleService.articleSave(article);
        return Response.getSuccessResponse();
    }

    /**
     * 编辑文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id:[\\d]+}/edit")
    @PermissionMenu("article")
    public Response edit(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        response.put("article", articleService.article(id));
        return response;
    }

    /**
     * 更新
     *
     * @param article
     * @return
     */
    @PutMapping
    @PermissionMenu({"article", "articleCheck"})
    @PermissionUser("admin")
    public Response update(Article article) {
        articleService.articleUpdate(article);
        return Response.getSuccessResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @PermissionMenu("article")
    @PermissionUser("admin")
    public Response delete(@RequestParam("id") Long id) {
        articleService.articleDelete(id);
        return Response.getSuccessResponse();
    }

    /**
     * 查找文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id:[\\d]+}")
    @PermissionMenu({"article", "articleCheck"})
    public Response get(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        Article article = articleService.article(id);
        article.setContent(MarkdownUtil.markdownToHtml(article.getContent()));
        response.put("article", article);
        return response;
    }

}
