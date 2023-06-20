package pt.solutions.af.appointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.solutions.af.user.model.customer.CustomerModel;
import pt.solutions.af.user.model.provider.ProviderModel;
import pt.solutions.af.work.model.WorkModel;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppointmentListView {

    public static final String FULL_NAME = "pt.solutions.af.appointment.model.AppointmentListView";

    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private ProviderModel provider;
    private CustomerModel customer;
    private WorkModel work;

    public AppointmentListView(Appointment appointment) {
        this.id = appointment.getId();
        this.startDate = appointment.getStartDate();
        this.endDate = appointment.getEndDate();
        this.status = appointment.getStatus().name();

        var providerApt = appointment.getProvider();
        this.provider = new ProviderModel(providerApt.getId(), providerApt.getFirstName());

        var customerApt = appointment.getCustomer();
        this.customer = new CustomerModel(customerApt.getId(), customerApt.getFirstName());

        var workApt = appointment.getWork();
        this.work = new WorkModel(workApt.getId(), workApt.getName());
    }
}
