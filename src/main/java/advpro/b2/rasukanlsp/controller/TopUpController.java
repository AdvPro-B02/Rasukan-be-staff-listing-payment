package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.enums.TopUpStatus;
import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/topup")
public class TopUpController {

    @Autowired
    private TopUpService topUpService;

    @GetMapping
    public ResponseEntity<List<TopUp>> getAllTopUp() {
        List<TopUp> body = topUpService.getAllTopUps();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getTopUpById(@PathVariable String id) {
        TopUp topUp;
        try {
            topUp = topUpService.getTopUpById(id);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("TopUp does not exist"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bodySuccess(topUp), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateTopUp(@PathVariable String id, @RequestParam TopUpStatus status) {
        TopUp topUp;
        try {
            topUp = topUpService.updateStatus(id, status);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("TopUp does not exist"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bodySuccess(topUp), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTopUp(@PathVariable String id) {
        try {
            topUpService.deleteTopUpById(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("Invalid id"), HttpStatus.BAD_REQUEST);
        }
        Map<String, String> body = new HashMap<>();
        body.put("success", "true");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createTopUp(@RequestParam String user, @RequestParam int amount) {
        TopUp topUp = topUpService.createTopUp(user, amount);
        return new ResponseEntity<>(bodySuccess(topUp), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TopUp>> getAllTopUpByUser(@PathVariable String userId) {
        List<TopUp> allTopUpByUser = topUpService.getAllTopUpByUser(userId);
        return new ResponseEntity<>(allTopUpByUser, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> deleteTopUpByUser(@PathVariable String userId) {
        try {
            topUpService.deleteTopUpByUser(userId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("Invalid user id"), HttpStatus.BAD_REQUEST);
        }
        Map<String, String> body = new HashMap<>();
        body.put("success", "true");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private Map<String, String> bodySuccess(TopUp topUp) {
        Map<String, String> body = new HashMap<>();
        body.put("success", "true");
        body.put("id", topUp.getId().toString());
        body.put("userId", topUp.getUserId().toString());
        body.put("amount", Integer.toString(topUp.getAmount()));
        body.put("status", topUp.getStatus().toString());
        return body;
    }

    private Map<String, String> bodyFail(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("success", "false");
        body.put("message", message);
        return body;
    }
}
