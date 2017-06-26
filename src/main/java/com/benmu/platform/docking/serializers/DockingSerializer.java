package com.benmu.platform.docking.serializers;

import java.lang.reflect.Type;

/**
 * Description: DockingSerializer
 *
 * 序列化器，i.e. JsonSerializer or XmlSerializer
 *
 * @author hanwei
 * @version 2017-03-28
 */
public interface DockingSerializer {

    String serialize(Object source);

    Object deserialize(Type type, String source) throws ClassNotFoundException;
}
