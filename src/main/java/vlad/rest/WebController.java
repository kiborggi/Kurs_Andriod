package vlad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import vlad.dto.AuthenticationRequestDto;
import vlad.model.Status;
import vlad.model.Survey.Answer;
import vlad.model.Survey.Question;
import vlad.model.Survey.Survey;
import vlad.model.rewiew.*;

import vlad.repository.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping(value = "/web")
public class WebController {

    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    RattemptQuestionAnswerRepository rattemptQuestionAnswerRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    RattemptRepository rattemptRepository;
    @Autowired
    AnswerRepository answerRepository;




    @RequestMapping(value = "/rewiew/{rewId}", method = RequestMethod.GET)
    public RedirectView rewiew(Model model, @PathVariable("rewId") long rewId, HttpServletRequest req) {
        if (surveyRepository.findById(rewId) != null) {

            if (req.getSession().getAttribute("rewiewSessionList") == null) {
                req
                        .getSession().setAttribute("rewiewSessionList", new ArrayList<RewiewSession>());
            }

            ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");

            RewiewSession rewiewSession = null;

            for (RewiewSession rewiewSession1 : rewiewSessionList) {
                if (rewiewSession1.getRewId() == rewId) {
                    rewiewSession = rewiewSession1;
                   // return new RedirectView("/web/rewiew/" + rewId);

                }
            }

            if (rewiewSession == null) {
                rewiewSession = new RewiewSession();
                rewiewSession.setRewId(rewId);
                rewiewSessionList.add(rewiewSession);
                return new RedirectView("/web/startRewiew/" + rewId );

            }
            else{
                return new RedirectView("/web/proccesReview/" + rewId );
            }


        }
        else {
            return new RedirectView("web/error/");
        }
    }




    @RequestMapping(value = "/startRewiew/{rewId}", method = RequestMethod.GET)
    public String startRewiew(Model model,
                              HttpServletRequest req,
                              @PathVariable("rewId") int rewId) {

        ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");
        RewiewSession rewiewSession = null;

        for (RewiewSession rewiewSession1 : rewiewSessionList) {
            if (rewiewSession1.getRewId() == rewId) {
                rewiewSession = rewiewSession1;
            }
        }
        rewiewSession.setRquestionIterator(questionRepository.findQuestionsBySurveyId(rewId).iterator());
        Iterator<Question> questionIterator = rewiewSession.getRquestionIterator();
        if(questionIterator.hasNext()){
            rewiewSession.setCurrentQuestion( questionIterator.next());
            return "greeting";
        }


        return "error";
    }

    @RequestMapping(value = "/processeFormStart", method = RequestMethod.POST)
    public RedirectView processeForm(Model model,@RequestParam(value = "name" ) String name,
                                     HttpServletRequest req,
                                     @RequestParam("rewId") String rewIdStr){
        ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");
        long rewId = Long.parseLong(rewIdStr);
        RewiewSession rewiewSession = null;
        System.out.println(name);
        System.out.println(rewId);
        for (RewiewSession rewiewSession1 : rewiewSessionList) {
            if (rewiewSession1.getRewId() == rewId) {
                rewiewSession = rewiewSession1;
            }
        }

        rewiewSession.setName(name);

        Rattempt rattempt = new Rattempt();

        rattempt.setUserName(name);
        rattempt.setCreated( new Date());
        rattempt.setSurveyId(rewId);
        rattempt.setStatus(Status.CREATED);
        rewiewSession.setRattempt(rattempt);
        rattemptRepository.saveAndFlush(rattempt);

        rewiewSession.setRattemptQuestionAnswerList(new ArrayList<RattemptQuestionAnswer>());

        return new RedirectView("/web/proccesReview/" + rewId);
    }


    @RequestMapping(value = "/proccesReview/{rewId}", method = RequestMethod.GET)
    public String rewiewPost(Model model,
                                   HttpServletRequest req,
                                   @PathVariable("rewId") long rewId) {

        ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");

        RewiewSession rewiewSession = null;

        for (RewiewSession rewiewSession1 : rewiewSessionList) {
            if (rewiewSession1.getRewId() == rewId) {
                rewiewSession = rewiewSession1;
            }
        }
        if (rewiewSession != null) {

            Question currentQuestion = null;
            Iterator<Question> questionIterator = rewiewSession.getRquestionIterator();

                currentQuestion = rewiewSession.getCurrentQuestion();

                ArrayList<Answer> answers = (ArrayList<Answer>) answerRepository.findAllByQuestionId(currentQuestion.getId());

                model.addAttribute("answers",answers);
                model.addAttribute("rewId",rewId);
                model.addAttribute("questionType",currentQuestion.getTypeOfQuestion().toString());

                if (rewiewSession.getStatus().equals(Status.ENDED.toString())){
                    return "error";
                }
                else{
                    return "rewiewForm";
                }

        }
        else {
            return "error";
        }
    }

    @RequestMapping(value = "/processeFormRewiew", method = RequestMethod.POST)
    public RedirectView processeFormRewiew(Model model,@RequestParam(value = "ids" , required = false) long[] ids,
                                     HttpServletRequest req, @RequestParam("rewId") String rewIdStr){
        ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");

        RewiewSession rewiewSession = null;
        long rewId = Long.parseLong(rewIdStr);
        for (RewiewSession rewiewSession1 : rewiewSessionList) {
            if (rewiewSession1.getRewId() == rewId) {
                rewiewSession = rewiewSession1;
            }
        }
        if (rewiewSession != null) {


            Iterator<Question> questionIterator = rewiewSession.getRquestionIterator();

            if (ids != null) {
                for (long i : ids){

                    RattemptQuestionAnswer rattemptQuestionAnswer = new RattemptQuestionAnswer();

                    rattemptQuestionAnswer.setQuestionId(rewiewSession.getCurrentQuestion().getId());
                    rattemptQuestionAnswer.setAnswerId(i);
                    rattemptQuestionAnswer.setAttemptId(rewiewSession.getRattempt().getId());

                    rattemptQuestionAnswerRepository.saveAndFlush(rattemptQuestionAnswer);

                }


            }
            if (questionIterator.hasNext()){
                rewiewSession.setCurrentQuestion(questionIterator.next());
            }
            else{

                rewiewSession.setStatus(Status.ENDED.toString());
                return new RedirectView("/web/rewiewResults/" + rewId);

            }





            return new RedirectView("/web/proccesReview/" + rewId);
        }
        else {
            return new RedirectView("web/error/");
        }
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model, @PathVariable("rewId") int rewId, HttpServletRequest req) {
        return "error";
    }

    @RequestMapping(value = "/rewiewResults/{rewId}", method = RequestMethod.GET)
    public String rewiewResults(Model model, @PathVariable("rewId") int rewId, HttpServletRequest req) {
        Survey survey = surveyRepository.findSurveyById(rewId);

        RewiewToDisplay rewiewToDisplay = new RewiewToDisplay();

        rewiewToDisplay.setName(survey.getName());
        rewiewToDisplay.setDescription(survey.getDescription());



        for (Question question : questionRepository.findQuestionsBySurveyId(rewId) ){
            QuestionToDisplay questionToDisplay = new QuestionToDisplay();
            questionToDisplay.setText(question.getText());

            int questionVotes = 0;


            for (Answer answer : answerRepository.findAllByQuestionId(question.getId())) {

                AnswerToDislpay answerToDislpay = new AnswerToDislpay();
                answerToDislpay.setText(answer.getText());

                int answerVotes = 0;

                for (RattemptQuestionAnswer rattemptQuestionAnswer : rattemptQuestionAnswerRepository.findAllByAnswerId(answer.getId())) {
                    answerVotes +=1;

                   answerToDislpay.getPeople().add( rattemptRepository.findById(rattemptQuestionAnswer.getAttemptId()).getUserName() );

                }

                answerToDislpay.setVotes( answerVotes);
                questionVotes += answerVotes;
                questionToDisplay.getAnswerToDislpayList().add(answerToDislpay);

            }
            questionToDisplay.setVotes(questionVotes);



            rewiewToDisplay.getQuestionToDisplayList().add(questionToDisplay);

        }


        for (QuestionToDisplay question : rewiewToDisplay.getQuestionToDisplayList()){
            for (AnswerToDislpay answer : question.getAnswerToDislpayList()){
                answer.setPercent(  (answer.getVotes()*1.0 / question.getVotes()*1.0 * 100));
            }
        }

        model.addAttribute("rewiew",rewiewToDisplay);



        return "rewiewResults";
    }


}
