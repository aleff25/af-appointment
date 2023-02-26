package pt.solutions.af.serviceType.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.serviceType.model.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {
}
