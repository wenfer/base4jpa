package site.qiuyuan.base4jpa.repo;


import org.springframework.util.StringUtils;
import site.qiuyuan.base4jpa.searcher.Conditional;
import site.qiuyuan.base4jpa.specification.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
public abstract class AbstractSearcherImpl<R> implements Conditional {

    CriteriaBuilder cb;

    private LinkedList<PredicateBuilder> wheres = new LinkedList<>();

    AbstractSearcherImpl(CriteriaBuilder cb) {
        this.cb = cb;
    }


    @Override
    public Where and() {
        Where and = Where.and();
        wheres.add(and);
        return and;
    }

    @Override
    public Where join(String property) {
        Where join = Where.join(property);
        wheres.add(join);
        return join;
    }

    @Override
    public JoinCollectionWhere joinCollection(String property) {
        JoinCollectionWhereImpl joinCollectionWhere = new JoinCollectionWhereImpl(property);
        wheres.add(joinCollectionWhere);
        return joinCollectionWhere;
    }


    @Override
    public Where or() {
        Where or = Where.or();
        wheres.add(or);
        return or;
    }

    @Override
    public void where(PredicateBuilder where) {
        this.wheres.add(where);
    }

    @Override
    public boolean clear() {
        try {
            this.wheres.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Predicate> buildPredicate(From<?, ?> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (PredicateBuilder where : wheres) {
            if (!StringUtils.isEmpty(where.getJoinProperty())) {
                Join<?, ?> join = root.join(where.getJoinProperty());
                buildSingleWhere(where, join, predicates);
            } else {
                buildSingleWhere(where, root, predicates);
            }
        }
        return predicates;
    }

    private void buildSingleWhere(PredicateBuilder where, From<?, ?> root, List<Predicate> predicates) {
        List<Term> builders = where.terms();
        Predicate[] predicateArray = new Predicate[builders.size()];
        int i = 0;

        for (Term term : builders) {
            Path<?> objectPath;
            if (where instanceof JoinCollectionWhere) {
                objectPath = root;
            } else {
                objectPath = root.get(term.getProperty());
            }
            if (objectPath == null) {
                throw new RuntimeException("不存在的字段:" + term.getProperty() + "，请检查是否拼错");
            }
            predicateArray[i] = term.getBuilder().build(cb, objectPath);
            i++;
        }

        if (OperatorEnum.AND.equals(where.operator())) {
            predicates.add(cb.and(predicateArray));
        } else if (OperatorEnum.OR.equals(where.operator())) {
            predicates.add(cb.or(predicateArray));
        }
    }

}
