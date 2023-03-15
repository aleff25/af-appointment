package pt.solutions.af.work.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;
import pt.solutions.af.user.model.provider.Provider;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "works")
@NoArgsConstructor
public class Work extends BaseEntity {

    private String name;
    private String description;
    private double price;
    private int duration;
    private String targetCustomer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "works_providers", joinColumns = @JoinColumn(name = "work_id"), inverseJoinColumns =
    @JoinColumn(name = "user_id"))
    private List<Provider> providers;

}
