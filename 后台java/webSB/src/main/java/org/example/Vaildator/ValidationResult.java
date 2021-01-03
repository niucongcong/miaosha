package org.example.Vaildator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class ValidationResult {
    private boolean hasErrors = false;
    private Map<String,String> errorMsgMap = new HashMap<>();
    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
