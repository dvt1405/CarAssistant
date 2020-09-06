package ai.kitt.vnest.basedata.entity;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import ai.api.model.AIContext;
import ai.api.model.AIOutputContext;

public class MyAIContext extends AIContext {

    public MyAIContext(AIOutputContext oc) {
        this.setLifespan(oc.getLifespan());
        this.setName(oc.getName());
        Map<String, JsonElement> mapParam = oc.getParameters();
        Map<String, String> myParam = new HashMap<>();
        for (String s : mapParam.keySet()) {
            myParam.put(s, mapParam.get(s).getAsString());
        }
        this.setParameters(myParam);
    }

}
