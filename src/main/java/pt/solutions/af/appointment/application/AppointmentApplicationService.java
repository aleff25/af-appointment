package pt.solutions.af.appointment.application;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.solutions.af.appointment.application.dto.AppointmentAvailableHoursDTO;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class AppointmentApplicationService {

    private AppointmentRepository repository;

    private UserApplicationService userService;

    private WorkApplicationService workService;


    public List<Appointment> getAppointmentByProviderId(String providerId) {
        return repository.findAllByProviderId(providerId);
    }

    public List<Appointment> getAppointmentByCustomerId(String customerId) {
        return repository.findAllByCustomerId(customerId);
    }

    public List<Appointment> getAppointmentsByProviderAtDay(String providerId, LocalDate day) {
        return repository.findByProviderIdWithStartInPeriod(providerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    public List<Appointment> getAppointmentsByCustomersAtDay(String customerId, LocalDate day) {
        return repository.findByCustomerIdWithStartInPeriod(customerId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    public void create(RegisterAppointmentDTO dto) {

        LocalDateTime startDate = LocalDateTime.ofInstant(dto.getStartDate(), ZoneId.systemDefault());

        AppointmentAvailableHoursDTO appointmentAvailableHoursDTO = AppointmentAvailableHoursDTO.of(dto.getCustomerId(),
                dto.getProviderId(), dto.getWorkId(), startDate);

        if (!isAvailable(appointmentAvailableHoursDTO)) {
            throw new AppointmentNotAvailableException();
        }
        Appointment appointment = Appointment.builder()
                .custumerId(dto.getCustomerId())
                .providerId(dto.getProviderId())
                .startDate(startDate)
                .build();

        appointment.newAppointment();

        log.info("Registering a new appointment Appointment=[{}]", appointment.toString());

        repository.save(appointment);

    }

    public void update(Appointment appointment) {
        repository.save(appointment);
    }

    public List<TimePeriod> getAvailableHours(AppointmentAvailableHoursDTO dto) {

        LocalDate date = dto.getStartDate().toLocalDate();

        Provider provider = (Provider) userService.findById(dto.getProviderId());
        WorkingPlan workingPlan = provider.getWorkingPlan();
        DayPlan dayPlan = workingPlan.getDay(date.getDayOfWeek().toString().toLowerCase());


        List<Appointment> providerAppointments = getAppointmentsByProviderAtDay(dto.getProviderId(), date);
        List<Appointment> customerAppointments = getAppointmentsByCustomersAtDay(dto.getCustomerId(), date);

        List<TimePeriod> availablePeriods = dayPlan.getTimePeriodsWithBreaksExcluded();
        availablePeriods = excludeAppointmentsFromTimePeriods(availablePeriods, providerAppointments);
        availablePeriods = excludeAppointmentsFromTimePeriods(availablePeriods, customerAppointments);

        return calculateAvailableHours(availablePeriods, workService.getById(dto.getWorkId()));
    }

    public boolean isAvailable(AppointmentAvailableHoursDTO dto) {
        if (!workService.isWorkForCustomer(dto.getWorkId(), dto.getCustomerId())) {
            return false;
        }
        Work work = workService.getById(dto.getWorkId());
        LocalTime start = dto.getStartDate().toLocalTime();
        TimePeriod timePeriod = new TimePeriod(start, start.plusMinutes(work.getDuration()));
        return getAvailableHours(dto).contains(timePeriod);
    }

    public List<TimePeriod> calculateAvailableHours(List<TimePeriod> availableTimePeriods, Work work) {
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

    public List<TimePeriod> excludeAppointmentsFromTimePeriods(List<TimePeriod> periods, List<Appointment> appointments) {

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

    public List<Appointment> getConfirmedAppointmentsByCustomerId(String customerId) {
        return repository.findConfirmedByCustomerId(customerId);
    }

}
