package com.kangyonggan.cs.service;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.ap.model.Article;
import com.kangyonggan.common.App;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 8/1/18
 */
@FeignClient(App.ARTICLE_PLATFORM)
public interface ArticleService {

    /**
     * 获取文章列表
     *
     * @param query
     * @return
     */
    @GetMapping("article")
    PageInfo<Article> articles(@RequestParam("query") Map<String, Object> query);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    @GetMapping("article/{id}")
    Article article(@PathVariable("id") Long id);

    /**
     * 获取下一篇文章
     *
     * @param id
     * @return
     */
    @GetMapping("article/{id}/next")
    Article articleNext(@PathVariable("id") Long id);

    /**
     * 获取上一篇文章
     *
     * @param id
     * @return
     */
    @GetMapping("article/{id}/prev")
    Article articlePrev(@PathVariable("id") Long id);

    /**
     * 文章审批
     *
     * @param type
     * @param replyMsg
     * @param ids
     */
    @PutMapping("article/reply")
    void articleReply(@RequestParam("type") String type, @RequestParam("replyMsg") String replyMsg,
                      @RequestParam("ids") String ids);

    /**
     * 保存文章
     *
     * @param article
     */
    @PostMapping("article")
    void articleSave(@RequestParam("article") Article article);

    /**
     * 更新文章
     *
     * @param article
     */
    @PutMapping("article")
    void articleUpdate(@RequestParam("article") Article article);

    /**
     * 删除文章
     *
     * @param id
     */
    @DeleteMapping("article/{id}")
    void articleDelete(@PathVariable("id") Long id);
}
