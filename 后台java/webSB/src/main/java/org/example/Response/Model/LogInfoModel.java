package org.example.Response.Model;

import lombok.Data;
import org.example.DataObject.ItemDO;
import org.example.DataObject.StockLogDO;

@Data
public class LogInfoModel {
    private ItemDO itemDO;
    private StockLogDO stockLogDO;
}
