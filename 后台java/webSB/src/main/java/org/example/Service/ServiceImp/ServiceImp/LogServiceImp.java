package org.example.Service.ServiceImp.ServiceImp;

import org.example.Dao.ItemDOMapper;
import org.example.Dao.StockLogDOMapper;
import org.example.DataObject.ItemDO;
import org.example.DataObject.StockLogDO;
import org.example.Response.Model.LogInfoModel;
import org.example.Service.ServiceImp.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class LogServiceImp implements LogService {


    @Autowired
    StockLogDOMapper stockLogDOMapper;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Override
    public ArrayList<LogInfoModel> getAllItem() {
        ArrayList<StockLogDO> data = stockLogDOMapper.selectAllLogInfo();
        ArrayList<LogInfoModel> itemVOList = (ArrayList<LogInfoModel>) data.stream().map(stockLogDO -> {
            LogInfoModel logInfoModel = new LogInfoModel();
            ItemDO itemDO = itemDOMapper.selectByPrimaryKey(stockLogDO.getItemId());
            logInfoModel.setItemDO(itemDO);
            logInfoModel.setStockLogDO(stockLogDO);
            return logInfoModel;
        }).collect(Collectors.toList());
        return itemVOList;
    }
}
