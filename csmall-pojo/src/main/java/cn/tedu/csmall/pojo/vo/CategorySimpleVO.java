package cn.tedu.csmall.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySimpleVO implements Serializable {

    private Long id;
    private Integer depth;
    private Integer isParent;
    private Long parentId;

}
