package com.miaoshaproject.miaosha.Service;

import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Service.Model.ItemModel;

import java.util.List;

public interface ItemService {
    ItemModel createItem(ItemModel itemModel) throws BusinessException;;

    List<ItemModel>listItem();

    ItemModel getItemById(Integer id);

    ItemModel getItemByIdInCache(Integer id);

    boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;

    boolean increaseStock(Integer itemId,Integer amount)throws BusinessException;

    boolean asyncDecreaseStock(Integer itemId,Integer amount);

    void increaseSales(Integer itemId,Integer amount)throws BusinessException;

    String initStockLog(Integer itemId,Integer amount);
}
