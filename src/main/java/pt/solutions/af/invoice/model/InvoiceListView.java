package pt.solutions.af.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InvoiceListView {

    public static final String FULL_NAME = "pt.solutions.af.invoice.model.InvoiceListView";

    private String id;
    private String number;
    private String status;
    private double totalAmount;
    private LocalDateTime issued;
}
