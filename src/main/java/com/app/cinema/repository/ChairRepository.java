package com.app.cinema.repository;

import com.app.cinema.Entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairRepository extends JpaRepository<Chair, Long> {
}
