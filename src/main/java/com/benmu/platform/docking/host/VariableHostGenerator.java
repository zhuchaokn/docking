package com.benmu.platform.docking.host;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.basic.PropertyHelper;
import com.benmu.platform.docking.context.DockingContext;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhuchao on 2017/4/7.
 */
@Service
public class VariableHostGenerator extends HostGenerator {

    @Resource
    private PropertyHelper propertyHelper;

    @Override
    public void generate(DockingMethod dockingMethod, DockingContext context) {
        String host = dockingMethod.getDocking().host();
        Map<String, String> pathVariableMap = Maps.newHashMap();
        context.getArgumentMap().forEach((key, value) -> {
            PathVariable path = value.getParameter().getAnnotation(PathVariable.class);
            if (path != null) {
                pathVariableMap.put(StringUtils.isNotBlank(path.value()) ? path.value() : key,
                        value.getObject().toString());
            }
        });
        host = propertyHelper.replace(host, pathVariableMap);
        context.setHost(host);
    }
}
