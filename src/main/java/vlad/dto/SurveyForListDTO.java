package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Status;
import vlad.model.Survey.Survey;
import vlad.repository.AttemptRepository;
import vlad.repository.QuestionRepository;
import vlad.repository.UserRepository;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyForListDTO {

    public Long id;
    public String description;
    public String name;
    public String ownerName;
    public long numberOfAttempts;
    public long numberOfQuestions;
    private Status status;
    private String type;

    public SurveyForListDTO(Survey survey, QuestionRepository questionRepository, AttemptRepository attemptRepository, UserRepository userRepository){
        this.id = survey.getId();
        this.description = survey.getDescription();
        this.name = survey.getName();
        this.ownerName = userRepository.findById(survey.getOwnerId()).getUsername();
        this.numberOfAttempts = attemptRepository.findAllBySurveyIdAndStatus(survey.getId(), Status.ENDED).size();
        this.numberOfQuestions = questionRepository.findQuestionsBySurveyId(survey.getId()).size();
        this.status = survey.getStatus();
        this.type = survey.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(long numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public long getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(long numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
