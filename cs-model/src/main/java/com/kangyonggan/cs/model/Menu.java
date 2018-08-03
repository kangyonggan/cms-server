package com.kangyonggan.cs.model;

import com.github.ofofs.jca.annotation.Serial;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
@Table(name = "tb_menu")
@Data
@Serial
public class Menu {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单代码
     */
    private String code;

    /**
     * 父菜单代码
     */
    private String pcode;

    /**
     * 菜单排序(从0开始)
     */
    private Integer sort;

    /**
     * 菜单图标的样式
     */
    private String icon;

    /**
     * 状态:{0:可用, 1:禁用}
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    /**
     * 子菜单
     */
    @Transient
    private List<Menu> children;

    private static final long serialVersionUID = 1L;
}