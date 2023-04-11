package pt.solutions.af.sms.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.solutions.af.sms.model.SmsRequest;

@Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSenderImpl smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}
