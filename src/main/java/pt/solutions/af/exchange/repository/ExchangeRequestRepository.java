package pt.solutions.af.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.exchange.model.ExchangeRequest;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, String> {
}
