package org.example.Response.Model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class ItemModel {

    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存不能不填")
    private Integer stock;

    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    private Integer sales;

    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

    private PromoModel promoModel;


}
