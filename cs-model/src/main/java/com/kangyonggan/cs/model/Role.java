package com.kangyonggan.cs.model;

import com.github.ofofs.jca.annotation.Serial;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
@Table(name = "tb_role")
@Data
@Serial
public class Role {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色代码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

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

    private static final long serialVersionUID = 1L;
}