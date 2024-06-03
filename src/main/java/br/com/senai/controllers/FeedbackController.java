package br.com.senai.controllers;

import br.com.senai.models.Feedback;
import br.com.senai.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @PostMapping(value = "/createFeedback",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @PutMapping(value = "/updateFeedback/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Feedback updateFeedback(@RequestBody Feedback feedback, @PathVariable Long id) {
        Feedback existingFeedback = feedbackRepository.findById(id).orElseThrow();
        existingFeedback.setName(feedback.getName());
        existingFeedback.setEmail(feedback.getEmail());
        existingFeedback.setCategory(feedback.getCategory());
        existingFeedback.setMessage(feedback.getMessage());

        return feedbackRepository.save(existingFeedback);
    }

    @DeleteMapping(value = "/deleteFeedback/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Feedback deleteFeedback(@PathVariable Long id) {
        Feedback existingFeedback = feedbackRepository.findById(id).orElseThrow();
        feedbackRepository.delete(existingFeedback);
        return existingFeedback;
    }

    @GetMapping(value = "/filtro/{palavra}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feedback> filtrarFeedback(@PathVariable String palavra) {
        return feedbackRepository.findByMessageContainingIgnoreCase(palavra);
    }
}
