package br.com.senai.repositories;

import br.com.senai.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMessageContainingIgnoreCase(String palavra);
}
