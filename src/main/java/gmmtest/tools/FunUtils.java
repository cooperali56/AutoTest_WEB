package gmmtest.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 小功能实现
 */
public class FunUtils {

    /**
     * 正则获取指定范围值
     *
     * @param value   需要处理的值
     * @param regular 正则表达式 (?<=(book_id=)).*?(?=(&))
     * @return 返回的值
     */
    public static String regularValue(String value, String regular) {
        Matcher matcher = Pattern.compile(regular).matcher(value);
        String value1 = null;
        if (matcher.find()) {
            value1 = matcher.group();
        }
        return value1;
    }
}