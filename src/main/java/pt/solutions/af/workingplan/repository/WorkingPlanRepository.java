package pt.solutions.af.workingplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.workingplan.model.WorkingPlan;

public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, String> {

    WorkingPlan findByProviderId(String providerId);

}
