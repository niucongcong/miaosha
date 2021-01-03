package org.example.Controller;


import org.example.Error.BusinessException;
import org.example.Response.CommonReturnType;
import org.example.Response.Model.ItemModel;
import org.example.Response.Model.ItemViewModel;
import org.example.Service.ServiceImp.ItemService;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
//单纯使用@CrossOrigin并没有解决跨域，需要添加后面的allowCredentials = "true", allowedHeaders = "*"
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController {
    private static Logger logger= LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.getAllItem();
        List<ItemViewModel> itemVOList =  itemModelList.stream().map(itemModel -> {
            ItemViewModel itemVO = this.convertFromItemModelToItemViewModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }


    @RequestMapping(value = "/publishpromo")
    @ResponseBody
    public CommonReturnType publishpromo(HttpServletRequest request,@RequestParam(name = "item_id")Integer id,
                                         @RequestParam(name = "promo_name")String promoName,
                                         @RequestParam(name = "price")Double price) throws ParseException, BusinessException {

        String user_id=request.getParameter("user_id");
        if (user_id==null|| user_id.equals("")){
            return CommonReturnType.create("用户信息丢失，请重新登录","failed");
        }

        String role_name= (String) redisTemplate.opsForValue().get("role_name_"+user_id);

        if (!role_name.equals("admin")){
            return CommonReturnType.create("您没有发布权限","failed");
        }

        if (price<=0){
            return CommonReturnType.create("价格比如大于0","failed");
        }
        itemService.publishItem(id,promoName,price);
        return CommonReturnType.create(null);
    }

    private ItemViewModel convertFromItemModelToItemViewModel(ItemModel itemModel){
        if (itemModel==null) return  null;
        ItemViewModel itemViewModel=new ItemViewModel();
        BeanUtils.copyProperties(itemModel, itemViewModel);
        if (itemModel.getPromoModel()!=null){
            itemViewModel.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemViewModel.setPromoId(itemModel.getPromoModel().getId());
            itemViewModel.setPromoName(itemModel.getPromoModel().getPromoName());
            itemViewModel.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemViewModel.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemViewModel.setPromoStatus(0);
        }
        return itemViewModel;
    }
}
