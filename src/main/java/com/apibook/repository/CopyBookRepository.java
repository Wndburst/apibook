package com.apibook.repository;

import com.apibook.entity.CopyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopyBookRepository extends JpaRepository<CopyBook, Integer> {
    List<CopyBook> findByBook_TitleContainingIgnoreCaseAndStateIsTrue(String title);
}