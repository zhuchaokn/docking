package com.benmu.platform.docking.head;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.serializers.SerializeMethod;
import com.benmu.platform.docking.context.DockingContext;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

/**
 * @author zhuchao on 2017/4/7.
 */
@Service
public class ContentTypeHeaderGenerator extends HeaderGenerator {

    @Resource
    private Map<SerializeMethod, String> contentTypeMap = Maps.newHashMap();

    @Override
    public void generate(DockingMethod dockingMethod, DockingContext context) {
        String contentType = contentTypeMap.get(dockingMethod.getMethodMapping().request());
        if (StringUtils.isNotBlank(contentType)) {
            Header header = new BasicHeader("Content-Type", contentType);
            context.getHeaderList().add(header);
        }
    }

    @PostConstruct
    public void init() {
        contentTypeMap.put(SerializeMethod.JSON, "application/json;");
        contentTypeMap.put(SerializeMethod.XML, "application/xml;");
    }
}
