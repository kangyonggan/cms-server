package com.kangyonggan.cs.service;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.common.App;
import com.kangyonggan.np.model.Category;
import com.kangyonggan.np.model.Novel;
import com.kangyonggan.np.model.Section;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 8/2/18
 */
@FeignClient(App.NOVEL_PLATFORM)
public interface NovelService {

    /**
     * 获取小说列表
     *
     * @param query
     * @return
     */
    @GetMapping("novel")
    PageInfo<Novel> novels(@RequestParam("query") Map<String, Object> query);

    /**
     * 获取小说
     *
     * @param code
     * @return
     */
    @GetMapping("novel/{code}")
    Novel novel(@PathVariable("code") Integer code);

    /**
     * 章节列表
     *
     * @param query
     * @return
     */
    @GetMapping("section")
    List<Section> sections(@RequestParam("query") Map<String, Object> query);

    /**
     * 章节详情
     *
     * @param code
     * @return
     */
    @GetMapping("section/{code}")
    Section section(@PathVariable("code") Integer code);

    /**
     * 拉取小说最新章节
     *
     * @param code
     */
    @PutMapping("novel/{code}/pull")
    void novelPull(@PathVariable("code") Integer code);

    /**
     * 上一章
     *
     * @param code
     * @return
     */
    @GetMapping("section/{code}/prev")
    Section sectionPrev(@PathVariable("code") Integer code);

    /**
     * 下一章
     *
     * @param code
     * @return
     */
    @GetMapping("section/{code}/next")
    Section sectionNext(@PathVariable("code") Integer code);

    /**
     * 栏目列表
     *
     * @param type
     * @return
     */
    @GetMapping("category")
    List<Category> categories(@RequestParam("type") String type);

    /**
     * 恢复/禁用
     *
     * @param id
     * @param status
     */
    @PutMapping(value = "{id}/status/{status}")
    void novelStatus(@RequestParam("id") Long id, @RequestParam("status") byte status);
}
