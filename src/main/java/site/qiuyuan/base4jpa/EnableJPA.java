package site.qiuyuan.base4jpa;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import site.qiuyuan.base4jpa.factory.BaseRepositoryFactoryBean;
import site.qiuyuan.base4jpa.repo.BaseRepositoryImpl;

import java.lang.annotation.*;

/**
 * @author qiuyuan
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DslConfiguration.class)
@EnableJpaRepositories(
        value = {"**.repository.**", "**.model"},
        repositoryBaseClass = BaseRepositoryImpl.class,
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND,
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
public @interface EnableJPA {
}
