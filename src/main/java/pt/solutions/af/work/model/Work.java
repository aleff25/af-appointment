package pt.solutions.af.work.model;

import lombok.Data;
import pt.solutions.af.commons.entity.BaseEntity;
import pt.solutions.af.user.model.User;

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
public class Work extends BaseEntity {

    private String name;
    private String description;
    private double price;
    private int duration;
    private String targetCustomer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "works_providers", joinColumns = @JoinColumn(name = "id_work"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> providers;

}
