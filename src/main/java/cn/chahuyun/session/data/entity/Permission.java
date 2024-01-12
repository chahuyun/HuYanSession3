package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
import jakarta.persistence.*;

/**
 * 权限信息
 *
 * @author Moyuyanli
 * @date 2024/1/9 14:52
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long qq;

    private boolean admin;

    private boolean session;

    private boolean dct;

    private boolean ds;

    private boolean hh;

    private boolean list;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




}
