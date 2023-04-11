package pt.solutions.af.sms.application;

import pt.solutions.af.sms.model.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);
}
