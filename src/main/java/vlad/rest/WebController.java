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
import vlad.model.rewiew.Ranswer;
import vlad.model.rewiew.RewiewSession;
import vlad.model.rewiew.Rquestion;
import vlad.repository.RAnswerRepository;
import vlad.repository.RQuestionRepository;
import vlad.repository.RewiewRepository;
import vlad.repository.SurveyRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/web")
public class WebController {

    @Autowired
    RewiewRepository rewiewRepository;
    @Autowired
    RQuestionRepository rQuestionRepository;
    @Autowired
    RAnswerRepository rAnswerRepository;

    @RequestMapping(value = "/rewiew/{rewId}", method = RequestMethod.GET)
    public RedirectView rewiew(Model model, @PathVariable("rewId") int rewId, HttpServletRequest req) {
        if (rewiewRepository.findById(rewId) != null) {

            if (req.getSession().getAttribute("rewiewSessionList") == null) {
                req
                        .getSession().setAttribute("rewiewSessionList", new ArrayList<RewiewSession>());
            }

            ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");

            RewiewSession rewiewSession = null;

            for (RewiewSession rewiewSession1 : rewiewSessionList) {
                if (rewiewSession1.getRewId() == rewId) {
                    rewiewSession = rewiewSession1;
                    return new RedirectView("web/rewiew/" + rewId);

                }
            }

            if (rewiewSession == null) {
                rewiewSession = new RewiewSession();
                rewiewSession.setRewId(rewId);
                rewiewSessionList.add(rewiewSession);
                return new RedirectView("/web/startRewiew" );

            }


        } else {
            return new RedirectView("web/error/");
        }
        return new RedirectView("web/error/");
    }




    @RequestMapping(value = "/startRewiew/{rewId}", method = RequestMethod.GET)
    public RedirectView startRewiew(Model model,
                              @RequestParam(value = "name" , required = false) String name ,
                              HttpServletRequest req,
                              @PathVariable("rewId") int rewId) {

        ArrayList<RewiewSession> rewiewSessionList = (ArrayList<RewiewSession>) req.getSession().getAttribute("rewiewSessionList");
        RewiewSession rewiewSession = null;

        for (RewiewSession rewiewSession1 : rewiewSessionList) {
            if (rewiewSession1.getRewId() == rewId) {
                rewiewSession = rewiewSession1;
            }
        }
        rewiewSession.setName(name);
        rewiewSession.setRquestionIterator(rQuestionRepository.findAllByRewievId(rewId).iterator());
        System.out.println("kikck");

        return new RedirectView("/web/proccesReview");
    }

    @RequestMapping(value = "/proccesReview", method = RequestMethod.POST)
    public String rewiewPost(Model model,@RequestParam(value = "ids" , required = false) int[] ids) {
        System.out.println("2");
        System.out.println(ids.length);
        List<Ranswer> rAnswerList = new ArrayList<>();
        Ranswer an1 = new Ranswer();
        an1.setText("Answer1");
        an1.setId(1l);

        Ranswer an2 = new Ranswer();
        an2.setText("Answer2");
        an2.setId(2l);
        rAnswerList.add(an1);
        rAnswerList.add(an2);

        model.addAttribute("answers",rAnswerList);

        model.addAttribute("name","vlad");
        return "greeting";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model, @PathVariable("rewId") int rewId, HttpServletRequest req) {
        return "error";
    }

}
