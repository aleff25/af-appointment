package pt.solutions.af.serviceType.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "service_type")
@Entity
@NoArgsConstructor
public class ServiceType {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private Effort effort;

    @Builder
    public ServiceType(final String name, final Effort effort) {
        this.name = name;
        this.effort = effort;
    }
}
