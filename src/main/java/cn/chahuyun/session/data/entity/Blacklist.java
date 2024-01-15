package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
import jakarta.persistence.*;

/**
 * 黑名单
 *
 * @author Moyuyanli
 * @date 2024/1/15 10:45
 */
@Entity
@Table(name = "black_list")
public class Blacklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
