package com.app.cinema.helper;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class EntitySpecification {

    public static <T> Specification<T> textAtLeastInOneColumn(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(root.getModel()
                .getDeclaredAttributes()
                .stream()
                .filter(a -> a.getJavaType().getSimpleName().equalsIgnoreCase("string"))
                .map(a -> criteriaBuilder.like(root.get(a.getName()), "%" + text.toLowerCase() + "%"))
                .toArray(Predicate[]::new));
    }
}
