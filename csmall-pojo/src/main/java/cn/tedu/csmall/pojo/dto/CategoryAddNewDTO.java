package cn.tedu.csmall.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryAddNewDTO implements Serializable {

    @NotNull(message = "添加类别失败，必须填写类别名称！") // 新增
    private String name;
    private Long parentId;
    private String keywords;
    private Integer sort;
    private String icon;
    private Integer isDisplay;

}
