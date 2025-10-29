package com.babsim.babsimbackend.domain.timeseries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// AI 생성: TimescaleDB 시계열 영양 데이터 리포지토리
@Repository
public interface NutritionTimeseriesRepository extends JpaRepository<NutritionTimeseries, NutritionTimeseriesId> {
}
