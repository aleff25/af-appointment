package pt.solutions.af.user.model.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "retail_customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class RetailCustomer extends Customer {

    private String vatNumber;
}
