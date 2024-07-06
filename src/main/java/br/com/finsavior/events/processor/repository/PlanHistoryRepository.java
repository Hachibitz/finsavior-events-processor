package br.com.finsavior.events.processor.repository;

import br.com.finsavior.events.processor.model.entity.PlanChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanHistoryRepository extends JpaRepository<PlanChangeHistory, Long> {
}
