package org.example.Service.ServiceImp;


import org.example.Error.BusinessException;
import org.example.Response.CommonReturnType;
import org.example.Response.Model.ItemModel;

import java.text.ParseException;
import java.util.ArrayList;

public interface ItemService {
    ItemModel getItemById(Integer id) throws BusinessException;
    ArrayList<ItemModel> getAllItem();
    CommonReturnType publishItem(Integer itemId, String promoName, Double price) throws ParseException, BusinessException;
}
