package com.benmu.platform.docking.serializers;

import com.benmu.web.spring.support.LazyMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Description: XmlSerializer
 * <p/>
 * Xml 序列化工具
 * <p/>
 * 如果发现有坑，可以参考 {@link org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter} 或者直接继承之
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class XmlSerializer implements DockingSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSerializer.class);
    private Map<String, Object> propertiesMap = Maps.newHashMap();
    private LazyMap<Type, JAXBContext> contextMap = new LazyMap<Type, JAXBContext>() {
        @Override
        protected JAXBContext load(Type type) {
            try {
                List<Class> clazzList = Lists.newArrayList();
                if (type instanceof ParameterizedType) {
                    Type[] typeList = ((ParameterizedType) type).getActualTypeArguments();
                    for (Type t : typeList) {
                        if (t instanceof ParameterizedType) {
                            t = ((ParameterizedType) t).getRawType();
                        }
                        clazzList.addAll(getDriveClassList((Class) t));
                    }
                    type = ((ParameterizedType) type).getRawType();
                }
                Class<?> key = (Class<?>) type;
                clazzList.addAll(getDriveClassList(key));
                Class[] clzzs = new Class[clazzList.size()];
                clzzs = clazzList.toArray(clzzs);
                CollectionUtils.reverseArray(clzzs);// 神奇的逆序,这个跟jaxb的实现有关,暂时还不知道类的先后顺序影响了什么
                return JAXBContext.newInstance(clzzs);
            } catch (JAXBException e) {
                LOGGER.info("error creating JAXBContext, class: {}", type.getTypeName());
                throw new RuntimeException(e);
            }
        }
    };
    @Override
    public String serialize(Object source) {
        try {
            Marshaller marshaller = createMarshaller(source.getClass());
            StringWriter writer = new StringWriter();
            marshaller.marshal(source, writer);
            return writer.toString();
        } catch (JAXBException e) {
            LOGGER.error("error during XML serializing, bean: {}", source.toString());
            throw new RuntimeException(e);
        }
    }

    public Marshaller marshaller(Class clazz) {
        return createMarshaller(clazz);
    }

    public Unmarshaller unmarshaller(Class clazz) {
        return createUnMarshaller(clazz);
    }

    @Override
    public Object deserialize(Type type, String source) {
        try {
            Unmarshaller unmarshaller = createUnMarshaller(type);
            return unmarshaller.unmarshal(new StringReader(source));
        } catch (JAXBException e) {
            LOGGER.error("error during XML deserializing, type: {}:{}", type.getTypeName(), source, e);
            return null;
        }
    }

    @Override
    public SerializeMethod method() {
        return SerializeMethod.XML;
    }

    @Override
    public String contentType() {
        return "text/xml";
    }

    @PostConstruct
    public void init() {
        Map<String, Object> properties = defaultProperties();
        propertiesMap.entrySet().stream().forEach(entry -> properties.put(entry.getKey(), entry.getValue()));
        this.propertiesMap = properties;
    }

    public Map<String, Object> defaultProperties() {
        Map<String, Object> map = Maps.newHashMap();
        map.put(Marshaller.JAXB_ENCODING, "UTF-8");
        map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 是否格式化生成的xml串
        map.put(Marshaller.JAXB_FRAGMENT, false); // 是否省略xm头声明信息
        return map;
    }

    public Map<String, Object> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, Object> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    public List<Class> getDriveClassList(Class clazz) {
        List<Class> classList = Lists.newArrayList();
        classList.add(clazz);
        for (int i = 0; i < 10; i++) {// 最多处理10重继承关系
            Class parentType = clazz.getSuperclass();
            Annotation root = parentType.getAnnotation(XmlRootElement.class);
            if (root == null) {
                break;
            }
            classList.add(parentType);
            clazz = parentType;
        }
        return classList;
    }

    private Marshaller createMarshaller(Class<?> aClass) {
        try {
            JAXBContext context = contextMap.get(aClass);
            Marshaller marshaller = context.createMarshaller();
            propertiesMap.entrySet().stream().forEach(entry -> {
                try {
                    marshaller.setProperty(entry.getKey(), entry.getValue());
                } catch (PropertyException e) {
                    LOGGER.error("error setting properties {}", entry);
                }
            });
            return marshaller;
        } catch (JAXBException e) {
            LOGGER.error("error creating marshaller, class: {}", aClass.getName());
            throw new RuntimeException(e);
        }
    }

    private Unmarshaller createUnMarshaller(Type type) {
        try {
            JAXBContext context = contextMap.get(type);
            return context.createUnmarshaller();
        } catch (JAXBException e) {
            LOGGER.error("error creating unmarshaller, class: {}", type.getTypeName());
            throw new RuntimeException(e);
        }
    }
}
