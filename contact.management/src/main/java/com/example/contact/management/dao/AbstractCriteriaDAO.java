package com.example.contact.management.dao;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractCriteriaDAO {

    @PersistenceContext
    protected EntityManager entityManager;


    protected <T>PageImpl<T> executeQuery(Pageable pageable, TypedQuery<T> query,TypedQuery<Long> countQuery){
        Long totalRows = (Long) countQuery.getSingleResult();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(),pageable,totalRows);
    }
}
