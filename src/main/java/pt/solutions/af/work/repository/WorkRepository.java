package pt.solutions.af.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.solutions.af.work.model.Work;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, String> {

    @Query("select w from Work w inner join w.providers p where p.id in :providerId")
    List<Work> findByProviderId(@Param("providerId") String providerId);

    @Query("select w from Work w where w.targetCustomer = :target ")
    List<Work> findByTargetCustomer(@Param("target") String targetCustomer);

    @Query("select w from Work w inner join w.providers p where p.id in :providerId and w.targetCustomer = :target ")
    List<Work> findByTargetCustomerAndProviderId(@Param("target") String targetCustomer, @Param("providerId") String providerId);

}
