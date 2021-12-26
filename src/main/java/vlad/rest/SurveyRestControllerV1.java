package vlad.rest;

import vlad.dto.*;
import vlad.dto.FotAttempt.*;
import vlad.model.Survey.*;
import vlad.model.User;
import vlad.repository.*;
import vlad.security.jwt.JwtTokenProvider;
import vlad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/survey/")
public class SurveyRestControllerV1 {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerTypeValueRepository answerTypeValueRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    AttemptRepository attemptRepository;
    @Autowired
    SurveyResultRepository surveyResultRepository;
    @Autowired
    AnswerRepository answerRepository;


    @Autowired
    public JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    @Autowired
    public SurveyRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getSurvey/{id}")
    public ResponseEntity<Survey> getSurvey(HttpServletRequest req,@PathVariable(name = "id") Long id){
        Survey res =  surveyRepository.findSurveyById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("/createSurvey")
    public ResponseEntity<Survey> createSurvey(@RequestBody SurveyDTO surveyDTO,HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);

        Survey surveyToCreate = SurveyDTO.getSurveuFromDTO(surveyDTO,user.getId());


        surveyRepository.saveAndFlush(surveyToCreate);

        return new ResponseEntity<>(surveyToCreate, HttpStatus.CREATED);
    }


    @PostMapping("/createQuestion")
    public ResponseEntity  createQuestion(@RequestBody QuestionDTO questionDTO, HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        long survOwnerId = surveyRepository.findSurveyById(questionDTO.getSurvetId()).getOwnerId();
        if(survOwnerId != user.getId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("acces error");
        }else{
            Question questionToCreate = QuestionDTO.questionFromDTO(questionDTO);
            questionRepository.saveAndFlush(questionToCreate);
            return new ResponseEntity<>(questionToCreate, HttpStatus.CREATED);
        }

    }
    @GetMapping("/getQuestionsOfSurvey/{id}")
    public ResponseEntity getAllSurveys(HttpServletRequest req,@PathVariable(name = "id") Long id){
        ArrayList<Question> questions = (ArrayList<Question>) questionRepository.findQuestionsBySurveyId(id);
        ArrayList<QuestionWithNuberOfAnswersDTO> questionWithNuberOfAnswersDTOArrayList = new ArrayList<>();
        if (questions != null){
            for (Question item: questions){
                questionWithNuberOfAnswersDTOArrayList.add(new QuestionWithNuberOfAnswersDTO(item,answerRepository));
            }
        }
        return  ResponseEntity.status(HttpStatus.OK)
                .body(questionWithNuberOfAnswersDTOArrayList);
    }
    @GetMapping("/getQuestion/{id}")
    public ResponseEntity<Question> getQuestion(HttpServletRequest req,@PathVariable(name = "id") Long id){
        Question res =  questionRepository.findQuestionsById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getAllSurveys")
    public ResponseEntity getAllSurveys(){
        return  ResponseEntity.status(HttpStatus.OK)
                .body(surveyRepository.findAll());
    }
    @GetMapping("/getAllSurveysOfUser")
    public ResponseEntity getAllSurveysOfUser(HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        List<Survey> surveys = surveyRepository.findSurveyByOwnerId(user.getId());
        return  ResponseEntity.status(HttpStatus.OK)
                .body(surveys);
    }



    //TO TEESTT!!!!!
    @PostMapping("/createTypeForSurvey")
    public ResponseEntity  createQuestion(@RequestBody TypeDTO typeDTO, HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        long survOwnerId = surveyRepository.findSurveyById(typeDTO.getSurveyId()).getOwnerId();
        if(survOwnerId != user.getId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("acces error");
        }else{
            Type typeToCreate = TypeDTO.getTypeFromDTO(typeDTO);
            typeRepository.saveAndFlush(typeToCreate);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(typeToCreate);
        }

    }

    @PostMapping("/createAnswerForQuestion")
    public ResponseEntity  createAnswer(@RequestBody AnswerDTO answerDTO, HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        Question question = questionRepository.findQuestionsById(answerDTO.getQuestionId());
        long survOwnerId = surveyRepository.findSurveyById(question.getSurveyId()).getOwnerId();
        System.out.println(answerDTO.getText());
        if(survOwnerId != user.getId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("acces error");
        }else{
            Answer answerToCreate = AnswerDTO.getAnswerFronDTO(answerDTO);
            answerRepository.saveAndFlush(answerToCreate);
            AnswerWithNumberOfTypesDTO answerWithNumberOfTypesDTO = new AnswerWithNumberOfTypesDTO(answerToCreate,answerTypeValueRepository);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(answerWithNumberOfTypesDTO);
        }

    }

    @PostMapping("/createAnswerTypeValue")
    public ResponseEntity  createAnswerTypeValue(@RequestBody AnswerTypeValueDTO answerTypeValueDTO, HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        Answer answer = answerRepository.findById(answerTypeValueDTO.getAnswerID());
        Question question = questionRepository.findQuestionsById(answer.getQuestionId());
        long survOwnerId = surveyRepository.findSurveyById(question.getSurveyId()).getOwnerId();
        if(survOwnerId != user.getId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("acces error");
        }else{
            AnswerTypeValue answerTypeValue = AnswerTypeValueDTO.getAnswerTypeValueFromDTO(answerTypeValueDTO);
            answerTypeValueRepository.saveAndFlush(answerTypeValue);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(answerTypeValue);
        }

    }

    @GetMapping("/getAnswersOfQuestion/{id}")
    public ResponseEntity getAnswersOfQuestion(HttpServletRequest req,@PathVariable(name = "id") Long id){
      List<Answer> answerList =  answerRepository.findAllByQuestionId(id);
      List<AnswerWithNumberOfTypesDTO> answerWithNumberOfTypesDTOList = new ArrayList<>();
      if (answerList != null){
          for(Answer item : answerList){
              answerWithNumberOfTypesDTOList.add(new AnswerWithNumberOfTypesDTO(item,answerTypeValueRepository));
          }
      }
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(answerWithNumberOfTypesDTOList);
    }
    @GetMapping("/getTypesOfSurvey/{id}")
    public ResponseEntity getTypesOfSurvey(HttpServletRequest req,@PathVariable(name = "id") Long id){
        List<Type> typeList = typeRepository.findAllBySurveyId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(typeList);
    }
    @GetMapping("/getAnswerTypeValueOfAnswer/{id}")
    public ResponseEntity getAnswerTypeValueOfAnswer(HttpServletRequest req,@PathVariable(name = "id") Long id){
        List<AnswerTypeValue> answerTypeValueList =  answerTypeValueRepository.findAllByAnswerId(id);
        List<AnswerTypeValueDTO> answerTypeValueDTOList = new ArrayList<>();
        for (AnswerTypeValue item : answerTypeValueList){
            System.out.println("-----  " + item.getTypeId());
            answerTypeValueDTOList.add( AnswerTypeValueDTO.getAnswerTypeValueFromDTO(item,typeRepository,answerRepository));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(answerTypeValueDTOList);
    }


    @GetMapping("/startAttemptForSurvey/{id}")
    public ResponseEntity startAttemptForSurvey(HttpServletRequest req,@PathVariable(name = "id") Long id){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
       Attempt attemptToStart = new Attempt();
       attemptToStart.setSurveyId(id);
       attemptToStart.setUserId(user.getId());
       Attempt savedAttempt = attemptRepository.saveAndFlush( attemptToStart);
       AttemptDTO attemptDTO = new AttemptDTO(savedAttempt);
       SurveyFotAttempt surveyFotAttempt = new SurveyFotAttempt(surveyRepository.findSurveyById(attemptDTO.getSurveyId()));
       List<Question> questionList = questionRepository.findQuestionsBySurveyId(surveyFotAttempt.getId());
       List<QuestionForAttempt> questionForAttemptList = new ArrayList<>();
        for(Question question : questionList){
            QuestionForAttempt questionForAttempt = new QuestionForAttempt(question);
                List<Answer> answerList = answerRepository.findAllByQuestionId(questionForAttempt.getId());
                List<AnswerForAttempt> answerForAttemptList = new ArrayList<>();
                for (Answer answer : answerList){
                    AnswerForAttempt answerForAttempt = new AnswerForAttempt(answer);
                    answerForAttemptList.add(answerForAttempt);
                }
                questionForAttempt.setAnswerForAttemptList(answerForAttemptList);
                questionForAttemptList.add(questionForAttempt);

        }

        surveyFotAttempt.setQuestionForAttemptList(questionForAttemptList);
        attemptDTO.setSurveyFotAttempt(surveyFotAttempt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attemptDTO);
    }


    @PostMapping("receiveAttemptResults")
    public ResponseEntity receiveAttemptResults(HttpServletRequest req, @RequestBody AttemptRespDTO attemptRespDTO) {
        String resultStr = "";

        List<QuestionRespDTO>  questionForAttemptList =  attemptRespDTO.getListQuestion();

        List<Type> typeList = typeRepository.findAllBySurveyId(attemptRespDTO.getSurveyId());

        Map<Long,Float> typeIdAndSumValues = new HashMap<Long,Float>();

        for(QuestionRespDTO item : questionForAttemptList){
            for(long answerId : item.getListAnswerId()){
               for(AnswerTypeValue answerTypeValue: answerTypeValueRepository.findAllByAnswerId(answerId)){
                   if(typeIdAndSumValues.get(answerTypeValue.getTypeId())==null){
                       typeIdAndSumValues.put(answerTypeValue.getTypeId(),answerTypeValue.getValue());
                   }
                   else {
                       float sum = typeIdAndSumValues.get(answerTypeValue.getTypeId());
                       sum+=answerTypeValue.getValue();
                       typeIdAndSumValues.replace(answerTypeValue.getTypeId(),sum);

                   }
               }
            }
        }

        for(Map.Entry<Long,Float> item : typeIdAndSumValues.entrySet()){
            List<SurveyResult> surveyResultList = surveyResultRepository.findAllByTypeId(item.getKey());

            for(SurveyResult surveyResult : surveyResultList){
                if (surveyResult.getValueFrom() <= item.getValue() && surveyResult.getValueTo() >= item.getValue()){
                    resultStr += surveyResult.getText() + "\n";
                }
            }

            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
        }
        TmpDTO tmpDTO = new TmpDTO();
        tmpDTO.setText(resultStr);
        System.out.println(resultStr);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tmpDTO);
    }

    @PostMapping("addSurveyResult")
    public ResponseEntity addSurveyResult(HttpServletRequest req, @RequestBody SurveyResultDTO surveyResultDTO) {
        SurveyResult surveyResulToSave = SurveyResultDTO.getSurveyResultFromDTO(surveyResultDTO);
        surveyResultRepository.saveAndFlush(surveyResulToSave);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(surveyResulToSave);
    }

    @GetMapping("getResultsOfSurvey/{id}")
    public ResponseEntity getResultsOfSurvey(HttpServletRequest req, @PathVariable(name = "id") Long id){
        List <SurveyResult> surveyResultList = new ArrayList<>();
        surveyResultList = surveyResultRepository.findAllBySurveyId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(surveyResultList);
    }

    @DeleteMapping ("/deleteSurvey/{id}")
    public ResponseEntity deleteSurvey(HttpServletRequest req,@PathVariable(name = "id") Long id){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        Survey surveyToDelete = surveyRepository.findSurveyById(id);
        if (surveyToDelete.getId() != user.getId()){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("acces error");
        }
        else {
            surveyRepository.delete(surveyToDelete);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("deleted");
        }
    }


}
