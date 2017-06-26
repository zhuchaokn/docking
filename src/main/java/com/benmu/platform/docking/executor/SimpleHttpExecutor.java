package com.benmu.platform.docking.executor;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.basic.Generator;
import com.benmu.platform.docking.body.BodyGenerator;
import com.benmu.platform.docking.context.DockingContext;
import com.benmu.platform.docking.context.DockingParam;
import com.benmu.platform.docking.cookie.CookieGenerator;
import com.benmu.platform.docking.head.HeaderGenerator;
import com.benmu.platform.docking.host.HostGenerator;
import com.benmu.platform.docking.http.DefaultHttpResponseParser;
import com.benmu.platform.docking.http.DefaultRequestBuilder;
import com.benmu.platform.docking.http.DockingHttpClient;
import com.benmu.platform.docking.http.HttpResponseParser;
import com.benmu.platform.docking.http.RequestBuilder;
import com.benmu.platform.docking.http.ResponseChecker;
import com.benmu.platform.docking.path.PathGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuchao on 2017/4/7.
 */
@Service
public class SimpleHttpExecutor implements HttpExecutor {

    @Autowired(required = false)
    private List<HostGenerator> hostGeneratorList = Lists.newArrayList();
    @Autowired(required = false)
    private List<PathGenerator> pathGeneratorList = Lists.newArrayList();
    @Autowired(required = false)
    private List<HeaderGenerator> headerGeneratorList = Lists.newArrayList();
    @Autowired(required = false)
    private List<CookieGenerator> cookieGeneratorList = Lists.newArrayList();
    @Autowired(required = false)
    private List<BodyGenerator> bodyGeneratorList = Lists.newArrayList();
    @Autowired(required = false)
    private RequestBuilder requestBuilder = new DefaultRequestBuilder();
    @Resource
    private DockingHttpClient dockingHttpClient;
    private List<Generator> generatorList = Lists.newArrayList();
    @Resource
    private List<ResponseChecker> responseCheckerList = Lists.newArrayList();
    @Autowired(required = false)
    private HttpResponseParser httpResponseParser = new DefaultHttpResponseParser();

    @Override
    public Object doExecute(DockingMethod dockingMethod, Object... args) {
        DockingContext context = new DockingContext();
        context.setArgumentMap(mapArgument(dockingMethod, args));
        generatorList.forEach(generator -> {
            generator.generate(dockingMethod, context);
        });
        HttpUriRequest request = requestBuilder.build(dockingMethod, context);
        HttpResponse response = dockingHttpClient.execute(request);
        responseCheckerList.forEach(checker -> {
            checker.check(response);
        });
        return httpResponseParser.parse(dockingMethod, response);
    }

    private Map<String, DockingParam> mapArgument(DockingMethod dockingMethod, Object[] args) {
        List<Parameter> parameterList = dockingMethod.getParameters();
        Map<String, DockingParam> parameterMap = Maps.newHashMap();
        int i = 0;
        for (Parameter para : parameterList) {
            parameterMap.put(para.getName(), new DockingParam(para.getName(), para, args[i++]));
        }
        return parameterMap;
    }

    @PostConstruct
    public void init() {
        generatorList.addAll(hostGeneratorList);
        generatorList.addAll(pathGeneratorList);
        generatorList.addAll(headerGeneratorList);
        generatorList.addAll(cookieGeneratorList);
        generatorList.addAll(bodyGeneratorList);
    }
}
