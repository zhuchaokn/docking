package com.benmu.platform.docking.basic;

import com.benmu.platform.docking.ConfigCenter;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author zhuchao on 2017/4/7.
 */
@Component
public class PropertyHelper {

    private static final Pattern pattern = Pattern.compile("\\$\\{\\w+\\}");
    @Resource
    private ConfigCenter configCenter;

    public String replace(String value, Map<String, String> extMap) {
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            String variable = matcher.group();
            String name = variable.substring(2, variable.length() - 1);
            String target = configCenter.getProperty(name);
            if (StringUtils.isNotBlank(target)) {
                value = StringUtils.replace(value, variable, target);
            } else {
                target = extMap.get(name);
                Preconditions
                        .checkArgument(StringUtils.isNotBlank(target), "没有找到" + name + "对应的替代项");
                value = StringUtils.replace(value, variable, target);
            }
        }
        return value;
    }
}
