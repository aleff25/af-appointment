package pt.solutions.af.serviceType.application;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.solutions.af.serviceType.application.dto.RegisterServiceTypeDTO;
import pt.solutions.af.serviceType.model.Effort;
import pt.solutions.af.serviceType.model.ServiceType;
import pt.solutions.af.serviceType.repository.ServiceTypeRepository;

@Service
@AllArgsConstructor
@Log4j2
public class ServiceTypeApplicationService {

    private ServiceTypeRepository repository;

    public void register(RegisterServiceTypeDTO dto) {

        ServiceType serviceType = ServiceType.builder()
                .name(dto.getName())
                .effort(Effort.of(dto.getServiceEffort()))
                .build();

        log.info("Creating new service type. Service Type [{}]", serviceType);

        repository.save(serviceType);
    }
}
