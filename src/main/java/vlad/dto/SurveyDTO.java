package vlad.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vlad.model.Survey.Survey;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyDTO {

    private String description;
    private String name;


    public static Survey getSurveuFromDTO(SurveyDTO surveyDTO, long ownerId){
        Survey ret = new Survey();
        ret.setDescription(surveyDTO.getDescription());
        ret.setName(surveyDTO.getName());
        ret.setOwnerId(ownerId);
        return  ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}