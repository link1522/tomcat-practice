package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String createQueryString(Map<String, String> queryMap) {
        List<String> queryList = new ArrayList<>();

        for (Map.Entry<String, String> query : queryMap.entrySet()) {
            String key = query.getKey();
            String value = query.getValue();

            if (isNotEmpty(value)) {
                queryList.add(key + "=" + value);
            }
        }

        if (queryList.size() != 0) {
            return "?" + String.join("&", queryList);
        }

        return "";
    }
}
