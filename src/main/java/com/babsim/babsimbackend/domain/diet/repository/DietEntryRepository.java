package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.DietEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// AI 생성: 식단 기록 데이터 접근 리포지토리
public interface DietEntryRepository extends JpaRepository<DietEntry, Long> {
    List<DietEntry> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
