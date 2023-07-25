package pt.solutions.af.appointment.application.validations;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pt.solutions.af.appointment.application.dto.RegisterAppointmentDTO;
import pt.solutions.af.appointment.exception.AppointmentNotAvailableException;
import pt.solutions.af.appointment.model.Appointment;
import pt.solutions.af.appointment.repository.AppointmentRepository;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.provider.Provider;
import pt.solutions.af.utils.TimePeriod;
import pt.solutions.af.work.application.WorkApplicationService;
import pt.solutions.af.work.model.Work;
import pt.solutions.af.workingplan.model.DayPlan;
import pt.solutions.af.workingplan.model.WorkingPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("ValidatorProviderAvailable")
@AllArgsConstructor
public class ValidatorProviderAvailable implements AppointmentSchedulerValidator {

    private AppointmentRepository repository;
    private WorkApplicationService workService;
    private UserApplicationService userService;

    @Override
    public void validate(RegisterAppointmentDTO dto) {

        LocalTime start = dto.getStartDate().toLocalTime();
        LocalTime end = dto.getEndDate().toLocalTime();
        TimePeriod timePeriod = new TimePeriod(start, end);
        boolean isAvailable = getAvailableHours(dto).contains(timePeriod);
        if (!isAvailable) {
            throw new AppointmentNotAvailableException();
        }
    }

    private List<TimePeriod> getAvailableHours(RegisterAppointmentDTO dto) {

        LocalDate date = dto.getStartDate().toLocalDate();

        Provider provider = (Provider) userService.findById(dto.getProviderId());
        WorkingPlan workingPlan = provider.getWorkingPlan();
        DayPlan dayPlan = workingPlan.getDay(date.getDayOfWeek().toString().toLowerCase());


        List<Appointment> providerAppointments = getAppointmentsByProviderAtDay(dto.getProviderId(), date);
        List<Appointment> customerAppointments = new ArrayList<>();
        if (StringUtils.isNotBlank(dto.getCustomerId())) {
            customerAppointments = getAppointmentsByCustomersAtDay(dto.getCustomerId(), date);
        }

        List<TimePeriod> availablePeriods = dayPlan.getTimePeriodsWithBreaksExcluded();
        availablePeriods = excludeAppointmentsFromTimePeriods(availablePeriods, providerAppointments);
        availablePeriods = excludeAppointmentsFromTimePeriods(availablePeriods, customerAppointments);

        return calculateAvailableHours(availablePeriods, workService.getById(dto.getWorkId()));
    }

    private List<TimePeriod> calculateAvailableHours(List<TimePeriod> availableTimePeriods, Work work) {
        List<TimePeriod> availableHours = new ArrayList<>();
        for (TimePeriod peroid : availableTimePeriods) {
            TimePeriod workPeroid = new TimePeriod(peroid.getStart(), peroid.getStart().plusMinutes(work.getDuration()));
            while (workPeroid.getEnd().isBefore(peroid.getEnd()) || workPeroid.getEnd().equals(peroid.getEnd())) {
                availableHours.add(new TimePeriod(workPeroid.getStart(), workPeroid.getStart().plusMinutes(work.getDuration())));
                workPeroid.setStart(workPeroid.getStart().plusMinutes(work.getDuration()));
                workPeroid.setEnd(workPeroid.getEnd().plusMinutes(work.getDuration()));
            }
        }
        return availableHours;
    }

    private List<TimePeriod> excludeAppointmentsFromTimePeriods(List<TimePeriod> periods, List<Appointment> appointments) {

        appointments = appointments.stream().sorted().collect(Collectors.toList());
        List<TimePeriod> toAdd = new ArrayList<>();

        for (Appointment appointment : appointments) {
            for (TimePeriod peroid : periods) {
                LocalDateTime startDate = appointment.getStartDate();
                LocalDateTime endDate = appointment.getEndDate();
                if ((startDate.toLocalTime().isBefore(peroid.getStart()) || startDate.toLocalTime().equals(peroid.getStart()))
                        && endDate.toLocalTime().isAfter(peroid.getStart()) && endDate.toLocalTime().isBefore(peroid.getEnd())) {
                    peroid.setStart(endDate.toLocalTime());
                }
                if (startDate.toLocalTime().isAfter(peroid.getStart()) && startDate.toLocalTime().isBefore(peroid.getEnd())
                        && endDate.toLocalTime().isAfter(peroid.getEnd()) || endDate.toLocalTime().equals(peroid.getEnd())) {
                    peroid.setEnd(startDate.toLocalTime());
                }
                if (startDate.toLocalTime().isAfter(peroid.getStart()) && startDate.toLocalTime().isBefore(peroid.getEnd())) {
                    toAdd.add(new TimePeriod(peroid.getStart(), startDate.toLocalTime()));
                    peroid.setStart(endDate.toLocalTime());
                }
            }
        }
        periods.addAll(toAdd);
        Collections.sort(periods);
        return periods;
    }

    private List<Appointment> getAppointmentsByProviderAtDay(String providerId, LocalDate day) {
        return repository.findByProviderIdWithStartInPeriod(providerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    private List<Appointment> getAppointmentsByCustomersAtDay(String customerId, LocalDate day) {
        return repository.findByCustomerIdWithStartInPeriod(customerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }
}
