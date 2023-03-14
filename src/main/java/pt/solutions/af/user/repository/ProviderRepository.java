package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.user.model.provider.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
}
