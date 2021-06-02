package com.cursor.homework19.repository;

import com.cursor.homework19.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    List<Laptop> findAllByMemorySizeIsGreaterThanEqual(int memorySize);

    List<Laptop> findAllByUsedIs(boolean used);
}
