package site.qiuyuan.base4jpa.searcher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public interface Searcher<R> extends Conditional {

    List<R> find();

    R findOne();

    long count();

    boolean exists();

    Page<R> page(Pageable request);
}
