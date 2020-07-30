package site.qiuyuan.base4jpa.searcher;


import site.qiuyuan.base4jpa.specification.JoinCollectionWhere;
import site.qiuyuan.base4jpa.specification.PredicateBuilder;
import site.qiuyuan.base4jpa.specification.Where;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface Conditional {

    void where(PredicateBuilder where);

    boolean clear();

    Where and();

    Where join(String property);

    JoinCollectionWhere joinCollection(String property);

    Where or();

}
