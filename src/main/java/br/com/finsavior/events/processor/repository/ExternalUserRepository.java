package br.com.finsavior.events.processor.repository;

import br.com.finsavior.events.processor.model.entity.ExternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExternalUserRepository extends JpaRepository<ExternalUser, Long> {
    @Query(value = "SELECT * FROM external_user WHERE subscription_id = :subscriptionId AND del_fg <> 'S'", nativeQuery = true)
    ExternalUser findBySubscriptionId(String subscriptionId);
}
