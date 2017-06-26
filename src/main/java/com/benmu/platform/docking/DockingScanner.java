package com.benmu.platform.docking;

import com.google.common.collect.Lists;
import com.benmu.platform.docking.annotations.Docking;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * Description: DockingScanner
 *
 * 扫描被 @Docker 注解标注的类
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class DockingScanner extends ClassPathBeanDefinitionScanner {

    private List<TypeFilter> typeFilterList = Lists.newArrayList(new AnnotationTypeFilter(Docking.class));

    public DockingScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public Set<BeanDefinitionHolder> scanPackages(String... basePackage) {
        return super.doScan(basePackage);
    }

    public List<TypeFilter> getTypeFilterList() {
        return typeFilterList;
    }

    public void setTypeFilterList(List<TypeFilter> typeFilterList) {
        this.typeFilterList = typeFilterList;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
    }

    @PostConstruct
    public void init() {
        if (CollectionUtils.isNotEmpty(typeFilterList)) {
            typeFilterList.stream().forEach(DockingScanner.this::addIncludeFilter);
        }
    }
}
