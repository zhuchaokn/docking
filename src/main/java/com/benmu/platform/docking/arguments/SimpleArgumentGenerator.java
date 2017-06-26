package com.benmu.platform.docking.arguments;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.context.DockingContext;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhuchao on 2017/4/7.
 */
@Resource
public class SimpleArgumentGenerator extends ArgumentGenerator {

    private static Joiner.MapJoiner joiner = Joiner.on("&").withKeyValueSeparator("=");

    @Override
    public void generate(DockingMethod dockingMethod, DockingContext context) {
        //这个只替换get的参数
        if (dockingMethod.getMethodMapping().method() != RequestMethod.GET) {
            return;
        }
        Map<String, String> argument = Maps.newHashMap();
        context.getArgumentMap().forEach((key, value) -> {
            if (value.getParameter().getAnnotation(PathVariable.class) != null) {
                return;
            }
            if (value.getParameter().getAnnotation(RequestBody.class) != null) {
                return;
            }
            argument.put(key, value.getObject().toString());
        });
        String arguments = joiner.join(argument);
        String path = context.getPath().endsWith("?") ? arguments : "?" + arguments;
        context.setPath(path);
    }
}
