package pt.solutions.af.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.work.application.WorkApplicationService;
import pt.solutions.af.work.model.Work;

@RestController
@RequestMapping(path = "/works")
public class WorkController {

    @Autowired
    private WorkApplicationService service;

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody Work dto) {
        service.create(dto);

        return ResponseEntity.ok().build();
    }

}
