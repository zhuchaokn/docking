package com.benmu.platform.docking.body;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.serializers.DockingSerializer;
import com.benmu.platform.docking.serializers.SerializeMethod;
import com.benmu.platform.docking.annotations.DockingMapping;
import com.benmu.platform.docking.context.DockingContext;
import com.benmu.platform.docking.context.DockingParam;
import com.benmu.platform.docking.serializers.SerializerCenter;
import com.google.common.collect.Iterables;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhuchao on 2017/4/7.
 */
public class SimpleBodyGenerator extends BodyGenerator {

    @Resource
    private SerializerCenter serializerCenter;

    @Override
    public void generate(DockingMethod dockingMethod, DockingContext context) {
        DockingMapping mapping = dockingMethod
                .getMethodMapping();
        SerializeMethod serializeMethod = mapping.request();
        if (context.getArgumentMap().isEmpty()) {
            return;
        }
        Object object;
        if (context.getArgumentMap().entrySet().size() == 1) {
            object = Iterables.getLast(context.getArgumentMap().entrySet()).getValue().getObject();
        } else {
            object = context.getArgumentMap().values().stream()
                    .filter(para -> para.getParameter().getAnnotation(
                            RequestBody.class) != null).map(DockingParam::getObject).findFirst()
                    .orElseGet(() -> null);
        }
        String name =
                serializeMethod == SerializeMethod.CUSTOMIZE ? mapping.requestCustomizeSerializer()
                        : serializeMethod.name();
        DockingSerializer serializer = serializerCenter
                .getSerializer(name);
        String body = serializer.serialize(object);
        context.setBody(body);
    }
}
