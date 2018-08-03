package com.kangyonggan.cs.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.common.Query;
import com.kangyonggan.common.Response;
import com.kangyonggan.cs.annotation.PermissionMenu;
import com.kangyonggan.cs.service.NovelService;
import com.kangyonggan.np.model.Novel;
import com.kangyonggan.np.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("content/novel")
public class ContentNovelController extends BaseController {

    @Autowired
    private NovelService novelService;

    /**
     * 小说列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("novel")
    public Response list() {
        Response response = Response.getSuccessResponse();
        PageInfo<Novel> pageInfo = novelService.novels(getQuery());

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 章节列表
     *
     * @param code
     * @return
     */
    @GetMapping("{code:[\\d]+}/sections")
    @PermissionMenu("novel")
    public Response sections(@PathVariable("code") Integer code) {
        Response response = Response.getSuccessResponse();
        Novel novel = novelService.novel(code);
        Query query = getQuery();
        query.put("novelCode", code);
        List<Section> sections = novelService.sections(query);

        response.put("novel", novel);
        response.put("sections", sections);
        return response;
    }

    /**
     * 章节详情
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}")
    @PermissionMenu("novel")
    public Response section(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = novelService.section(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 上一章节
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}/prev")
    @PermissionMenu("novel")
    public Response prev(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = novelService.sectionPrev(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 下一章节
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}/next")
    @PermissionMenu("novel")
    public Response next(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = novelService.sectionNext(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 恢复/禁用
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping(value = "{id:[\\d]+}/status/{status:\\b0\\b|\\b1\\b}")
    @PermissionMenu("novel")
    public Response status(@PathVariable("id") Long id, @PathVariable("status") byte status) {
        novelService.novelStatus(id, status);
        return Response.getSuccessResponse();
    }

    /**
     * 拉取最新章节
     *
     * @param code
     * @return
     */
    @PutMapping(value = "{novelCode:[\\d]+}/pull")
    @PermissionMenu("novel")
    public Response pull(@PathVariable("code") Integer code) {
        novelService.novelPull(code);
        return Response.getSuccessResponse();
    }

}
