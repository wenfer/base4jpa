package site.qiuyuan.base4jpa.core;

import site.qiuyuan.base4jpa.specification.Where;

import javax.persistence.criteria.Subquery;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface SubQueryer<R> {

    Where where();

    Subquery<R> getQuery();
}
