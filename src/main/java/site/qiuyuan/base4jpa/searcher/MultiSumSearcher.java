package site.qiuyuan.base4jpa.searcher;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface MultiSumSearcher<R> extends Conditional{

    R multiSum(String...property);
}
