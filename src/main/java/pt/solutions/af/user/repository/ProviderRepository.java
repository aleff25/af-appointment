package pt.solutions.af.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.user.model.provider.ProviderListView;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, String> {

    @Query("SELECT new " + ProviderListView.FULL_NAME + "(u)" +
            " FROM User u WHERE u.provider = true")
    List<ProviderListView> findAllProviders();
}
