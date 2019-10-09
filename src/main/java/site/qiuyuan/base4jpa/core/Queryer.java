package site.qiuyuan.base4jpa.core;

import site.qiuyuan.base4jpa.specification.Where;

import java.util.List;

/**
 * <p>
 * 查询器
 *
 * @param <T> 结果类型
 *            </p>
 * @author qiuyuan
 * @since 1.0
 */
public interface Queryer<R> {

    void setWhere(Where where);

    Where where();

    Where join(String property);

    R findOne();

    List<R> findAll();

    <SF, SR> SubQueryer<SR> subQuery(String property, Class<SF> from, Class<SR> result);
}
