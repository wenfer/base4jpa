package site.qiuyuan.base4jpa.repo;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import site.qiuyuan.base4jpa.searcher.Searcher;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Slf4j
class DefaultSearcherImpl<R> extends AbstractSearcherImpl<R> implements Searcher<R> {

    private List<Order> orders = new ArrayList<>();

    private Class<R> beanClass;

    private final EntityManager entityManager;

    DefaultSearcherImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder, Class<R> beanClass) {
        super(criteriaBuilder);
        this.entityManager = entityManager;
        this.beanClass = beanClass;
    }

    @Override
    public List<R> find() {
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        return createQuery(query, root).getResultList();
    }

    @Override
    public R findOne() {
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        List<R> resultList = createQuery(query, root).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        if (resultList.size() > 1) {
            log.error("此条数据不唯一,请及时处理");
            throw new NonUniqueResultException(resultList.size());
        }
        return resultList.get(0);
    }

    @Override
    public long count() {
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        Root<?> root = count.from(beanClass);
        count.select(cb.count(root));
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            count.where(predicates.toArray(predicateArray));
        }
        TypedQuery<Long> countQuery = entityManager.createQuery(count);
        return countQuery.getSingleResult();
    }


    @Override
    public boolean exists() {
        CriteriaQuery<Boolean> exists = cb.createQuery(Boolean.class);
        Root<R> root = exists.from(beanClass);
        exists.select(cb.gt(cb.count(root), 0));
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            exists.where(predicates.toArray(predicateArray));
        }
        TypedQuery<Boolean> existsQuery = entityManager.createQuery(exists);
        return existsQuery.getSingleResult();
    }

    @Override
    public Page<R> page(Pageable pageRequest) {
        Sort sort = pageRequest.getSort();
        CriteriaQuery<R> query = cb.createQuery(beanClass);
        Root<R> root = query.from(beanClass);
        TypedQuery<R> typedQuery = createQuery(query, root);

        if (!sort.isSorted()) {
            List<Order> collect = sort.map(s -> new OrderImpl(root.get(s.getProperty()), s.isAscending())).get().collect(Collectors.toList());
            query.orderBy(collect);
        }
        typedQuery.setMaxResults(pageRequest.getPageSize());
        typedQuery.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        List<R> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageRequest, count());
    }


    private TypedQuery<R> createQuery(CriteriaQuery<R> query, From<?, ?> root) {
        List<Predicate> predicates = buildPredicate(root);
        if (!CollectionUtils.isEmpty(predicates)) {
            Predicate[] predicateArray = new Predicate[predicates.size()];
            query.where(predicates.toArray(predicateArray));
        }
        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query);
    }


}
