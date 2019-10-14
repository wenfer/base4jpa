package site.qiuyuan.base4jpa.specification;

import site.qiuyuan.base4jpa.enums.Position;

import java.util.Date;

/**
 * <p>
 * <F> from的表
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public interface Where<F> {

    /**
     * 构建or查询
     * or().is("p","1").is("p","2")
     */
    Where<F> or();

    Where<F> is(String property, Object value);

    /**
     * A列 大等于 B列
     */
    Where<F> gteAtoB(String propertyA, String propertyB);

    Where<F> in(String property, Object... value);

    Where<F> isNull(String property, boolean isNull);

    Where<F> like(String property, String value, Position position);

    Where<F> gte(String property, Number value);

    Where<F> gt(String property, Number value);

    Where<F> lte(String property, Number value);

    Where<F> lt(String property, Number value);

    /**
     * 起始时间查询
     */
    Where<F> start(String property, Date time);

    /**
     * 结束时间查询
     */
    Where<F> end(String property, Date time);



}
