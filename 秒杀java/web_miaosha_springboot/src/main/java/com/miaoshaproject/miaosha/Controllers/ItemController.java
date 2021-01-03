package com.miaoshaproject.miaosha.Controllers;


import com.miaoshaproject.miaosha.Controllers.ViewModel.ItemViewModel;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Response.CommonReturnType;
import com.miaoshaproject.miaosha.Service.CacheService;
import com.miaoshaproject.miaosha.Service.ItemService;
import com.miaoshaproject.miaosha.Service.Model.ItemModel;
import com.miaoshaproject.miaosha.Service.PromoService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true" )
public class ItemController extends BaseController{
    @Autowired
    private ItemService itemService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private PromoService promoService;


    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        itemModel=itemService.createItem(itemModel);
        ItemViewModel itemViewModel=this.convertFromItemModelToItemViewModel(itemModel);
        return CommonReturnType.create(itemViewModel);
    }

    @RequestMapping(value = "/publishpromo",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType publishpromo(@RequestParam(name = "id")Integer id){
        promoService.publishPromo(id);
        return CommonReturnType.create(null);

    }


    @RequestMapping(value = "/get",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel itemModel = null;
        System.out.println("get item from cache");
        itemModel = (ItemModel) cacheService.getFromCommonCache("item_"+id);
        if(itemModel == null){
            System.out.println("get item from cache failed");
            System.out.println("get item from redis");
            itemModel = (ItemModel) redisTemplate.opsForValue().get("item_"+id);
            if(itemModel == null){
                System.out.println("get item from redis failed");
                System.out.println("get item from mysql");
                itemModel = itemService.getItemById(id);
                redisTemplate.opsForValue().set("item_"+id,itemModel);
                redisTemplate.expire("item_"+id,180, TimeUnit.SECONDS);
            }
            cacheService.setCommonCache("item_"+id,itemModel);
        }
        ItemViewModel itemViewModel = this.convertFromItemModelToItemViewModel(itemModel);
        return CommonReturnType.create(itemViewModel);

    }

    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemViewModel> itemVOList =  itemModelList.stream().map(itemModel -> {
            ItemViewModel itemVO = this.convertFromItemModelToItemViewModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }

    private ItemViewModel convertFromItemModelToItemViewModel(ItemModel itemModel){
        if (itemModel==null) return  null;
        ItemViewModel itemViewModel=new ItemViewModel();
        BeanUtils.copyProperties(itemModel, itemViewModel);
        if (itemModel.getPromoModel()!=null){
            itemViewModel.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemViewModel.setPromoId(itemModel.getPromoModel().getId());
            itemViewModel.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemViewModel.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemViewModel.setPromoStatus(0);
        }
        return itemViewModel;
    }
}
