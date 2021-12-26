package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import vlad.model.Survey.AnswerTypeValue;
import vlad.repository.AnswerRepository;
import vlad.repository.TypeRepository;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerTypeValueDTO {



    private long answerID;

    private long typeID;

    private float value;

    private String typeText;

    private String ansewerText;

    public static AnswerTypeValueDTO getAnswerTypeValueFromDTO(AnswerTypeValue answerTypeValue, TypeRepository typeRepository, AnswerRepository answerRepository){

        AnswerTypeValueDTO ret = new AnswerTypeValueDTO();
        ret.setAnswerID(answerTypeValue.getAnswerID());
        ret.setValue(answerTypeValue.getValue());
        ret.setTypeID(answerTypeValue.getTypeId());
        ret.setTypeText(typeRepository.findById(answerTypeValue.getTypeId()).getText());
        ret.setAnsewerText(answerRepository.findById(answerTypeValue.getAnswerID()).getText());
        return ret;
    }

    public static AnswerTypeValue getAnswerTypeValueFromDTO(AnswerTypeValueDTO dto){
        AnswerTypeValue ret = new AnswerTypeValue();
        ret.setAnswerId(dto.getAnswerID());
        ret.setValue(dto.getValue());
        ret.setTypeId(dto.getTypeID());
        return ret;
    }



}
