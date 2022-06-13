package vlad.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import vlad.dto.*;
import vlad.dto.FotAttempt.*;
import vlad.model.Status;
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

import java.util.*;

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
    UserRepository userRepository;


    @Autowired
    public JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    @Autowired
    public SurveyRestControllerV1(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Получить опрос", notes = "Получить опрос по его Id")
    @ApiImplicitParam(name = "id", value = "Survey  ID", required = true, dataType = "Integer", paramType = "path")
    @GetMapping("/getSurvey/{id}")
    public ResponseEntity<Survey> getSurvey(HttpServletRequest req,@PathVariable(name = "id") Long id){
        Survey res =  surveyRepository.findSurveyById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value = "Создать опрос", notes = "")
    @ApiImplicitParam(name = "SurveyDTO", value = "Survey DTO", required = true, dataType = "Integer", paramType = "path")
    @PostMapping("/createSurvey")
    public ResponseEntity<Survey> createSurvey(@RequestBody SurveyDTO surveyDTO,HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);

        Survey surveyToCreate = SurveyDTO.getSurveuFromDTO(surveyDTO,user.getId());


        surveyRepository.saveAndFlush(surveyToCreate);

        return new ResponseEntity<>(surveyToCreate, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Изменить статус опроса", notes = "Изменить статус опроса по его id")
    @ApiImplicitParam(name = "id", value = "Survey  ID", required = true, dataType = "Integer", paramType = "path")
    @PostMapping("/changeSurveyStatus/{id}")
    public ResponseEntity<Survey> changeSurveyStatus(HttpServletRequest req,@PathVariable(name = "id") Long id){
        Survey survey = surveyRepository.findSurveyById(id);
        if (survey.getStatus().equals(Status.NOT_ACTIVE)) {
            survey.setStatus(Status.ACTIVE);
        }
        else {
            survey.setStatus(Status.NOT_ACTIVE);
        }
        surveyRepository.saveAndFlush(survey);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }


    @ApiOperation(value = "Создать вопрос", notes = "")
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
            if (questionDTO.getTypeOfQuestion().equals(TypeOfQuestionEnum.NUMERIC)){
                Answer answer = new Answer();
                answer.setText("answer_for_numeric");
                answer.setQuestionId(questionToCreate.getId());
                answerRepository.saveAndFlush(answer);
                answerRepository.saveAndFlush(answer);
            }
            return new ResponseEntity<>(questionToCreate, HttpStatus.CREATED);
        }

    }
    @ApiOperation(value = "Получить все вопросы опроса по его id", notes = "")
    @ApiImplicitParam(name = "id", value = "Survey  ID", required = true, dataType = "Integer", paramType = "path")
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

    @ApiOperation(value = "Получить все вопрос по его id", notes = "")
    @GetMapping("/getQuestion/{id}")
    public ResponseEntity<Question> getQuestion(HttpServletRequest req,@PathVariable(name = "id") Long id){
        Question res =  questionRepository.findQuestionsById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @ApiOperation(value = "Получить все опросы со статусом ACTIVE", notes = "")
    @GetMapping("/getAllSurveys")
    public ResponseEntity getAllSurveys(){
        ArrayList<SurveyForListDTO> ret = new ArrayList<SurveyForListDTO>();
        for (Survey survey : surveyRepository.findAllByStatus(Status.ACTIVE)){
            ret.add(new SurveyForListDTO(survey,questionRepository,attemptRepository,userRepository));
        }
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ret);
    }

    @ApiOperation(value = "Получить все опросы со статусом пользователя", notes = "")
    @GetMapping("/getAllSurveysOfUser")
    public ResponseEntity getAllSurveysOfUser(HttpServletRequest req){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        List<Survey> surveys = surveyRepository.findSurveyByOwnerId(user.getId());
        ArrayList<SurveyForListDTO> ret = new ArrayList<SurveyForListDTO>();
        for (Survey survey : surveyRepository.findAll()){
            ret.add(new SurveyForListDTO(survey,questionRepository,attemptRepository,userRepository));
        }
        return  ResponseEntity.status(HttpStatus.OK)
                .body(ret);
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
       Date date = new Date();
       attemptToStart.setCreated(date);
       Attempt savedAttempt = attemptRepository.saveAndFlush( attemptToStart);
       AttemptDTO attemptDTO = new AttemptDTO(savedAttempt);
       SurveyFotAttempt surveyFotAttempt = new SurveyFotAttempt(surveyRepository.findSurveyById(attemptDTO.getSurveyId()));
       List<Question> questionList = questionRepository.findQuestionsBySurveyId(surveyFotAttempt.getId());
       List<QuestionForAttempt> questionForAttemptList = new ArrayList<>();
        for(Question question : questionList){
            QuestionForAttempt questionForAttempt = new QuestionForAttempt(question);
                List<Answer> answerList = answerRepository.findAllByQuestionId(questionForAttempt.getId());
                List<AnswerForAttempt> answerForAttemptList = new ArrayList<>();
            questionForAttempt.setNumTo(question.getNumTo());
            questionForAttempt.setNumFrom(question.getNumFrom());

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
                   System.out.println("answID" +  answerTypeValue.getAnswerId()  + " value " + answerTypeValue.getValue() +" num value " + item.getNumValue());
                   if(typeIdAndSumValues.get(answerTypeValue.getTypeId())==null){
                       if(item.getTypeOfQuestion().equals(TypeOfQuestionEnum.NUMERIC)){
                           typeIdAndSumValues.put(answerTypeValue.getTypeId(),answerTypeValue.getValue()*item.getNumValue());
                       }else
                           {
                       typeIdAndSumValues.put(answerTypeValue.getTypeId(),answerTypeValue.getValue());}

                   }
                   else {
                       if(item.getTypeOfQuestion().equals(TypeOfQuestionEnum.NUMERIC)){
                           float sum = typeIdAndSumValues.get(answerTypeValue.getTypeId());
                           sum += answerTypeValue.getValue()*item.getNumValue();
                           typeIdAndSumValues.replace(answerTypeValue.getTypeId(), sum);
                       }else {
                           float sum = typeIdAndSumValues.get(answerTypeValue.getTypeId());
                           sum += answerTypeValue.getValue();
                           typeIdAndSumValues.replace(answerTypeValue.getTypeId(), sum);
                       }
                   }
               }
            }
        }

        Attempt attempt = attemptRepository.findAllById(attemptRespDTO.getAttemptId());
        for(Map.Entry<Long,Float> item : typeIdAndSumValues.entrySet()){
            List<SurveyResult> surveyResultList = surveyResultRepository.findAllByTypeId(item.getKey());

            for(SurveyResult surveyResult : surveyResultList){
                if (surveyResult.getValueFrom() <= item.getValue() && surveyResult.getValueTo() >= item.getValue()){
                    resultStr += surveyResult.getText() + "\n";
                }
            }

            System.out.printf("Key: %s  Value: %s \n", item.getKey(), item.getValue());
        }
        attempt.setStatus(Status.ENDED);
        Date date = new Date();
        attempt.setEnded(date);
        attempt.setResultText(resultStr);
        TmpDTO tmpDTO = new TmpDTO();
        tmpDTO.setText(resultStr);
        System.out.println(resultStr);
        attemptRepository.saveAndFlush(attempt);
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
        List<SurveyResultToSendDTO> surveyResultDTOList = new ArrayList<>();
        for (SurveyResult surveyResult : surveyResultList){
            surveyResultDTOList.add(new SurveyResultToSendDTO(surveyResult,typeRepository));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(surveyResultDTOList);
    }

    @GetMapping("getAttemptsOfSurvey/{id}")
    public ResponseEntity getAttemptsOfSurvey(HttpServletRequest req, @PathVariable(name = "id") Long id){
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        User user = userService.findByUsername(username);
        List<Attempt> attemptArrayList =  attemptRepository.findAllBySurveyIdAndStatusAndUserId(id,Status.ENDED,user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attemptArrayList);
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
