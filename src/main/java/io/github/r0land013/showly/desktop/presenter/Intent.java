package io.github.r0land013.showly.desktop.presenter;

import java.util.Map;
import java.util.HashMap;

public class Intent {
    
    private Map<String, Object> data;

    public Intent() {
        data = new HashMap<String, Object>();
    }

    public void addData(String key, Object object) {
        data.put(key, object);
    }

    public Object getData(String key) {
        return data.get(key);
    }

}
