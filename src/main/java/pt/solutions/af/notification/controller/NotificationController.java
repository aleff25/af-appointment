package pt.solutions.af.notification.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.solutions.af.notification.application.NotificationApplicationService;
import pt.solutions.af.notification.model.Notification;
import pt.solutions.af.user.application.UserApplicationService;
import pt.solutions.af.user.model.auth.AuthUserView;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationApplicationService notificationService;
    private final UserApplicationService userService;

    @GetMapping()
    public String showUserNotificationList(Model model, @AuthenticationPrincipal AuthUserView currentUser) {
        model.addAttribute("notifications", userService.findById(currentUser.getId()).getNotifications());
        return "notifications/listNotifications";
    }

    @GetMapping("/{notificationId}")
    public String showNotification(@PathVariable("notificationId") String notificationId, @AuthenticationPrincipal AuthUserView currentUser) {
        Notification notification = notificationService.getNotificationById(notificationId);
        notificationService.markAsRead(notificationId, currentUser.getId());
        return "redirect:" + notification.getUrl();
    }

    @PostMapping("/markAllAsRead")
    public String processMarkAllAsRead(@AuthenticationPrincipal AuthUserView currentUser) {
        notificationService.markAllAsRead(currentUser.getId());
        return "redirect:/notifications";
    }
}
