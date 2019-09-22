package main.controllers;

import main.entities.Tube;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import main.repositories.TubeRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    private TubeRepository tubeRepository;

    public HomeController() {
        this.tubeRepository = new TubeRepository();
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request, Model model) {
        if (request.getSession() == null) {
            return super.view(request, model, "index");
        }

        return super.redirect(request, model, "home");
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        if (request.getSession() == null) {
            return super.redirect(request, model, "");
        }

        List<Tube> allVideos = this.tubeRepository
                .findAllTubes();

        model.addAttribute("username", request.getSession().getAttributes().get("username"));

        if (allVideos.size() == 0) {
            model.addAttribute("allTubes", "There are no videos.");
        } else {
            model.addAttribute("allTubes", String.join("", allVideos
                    .stream()
                    .map(x -> "<div class=\"col-md-4\"><a href=\"/tube/details/"
                            + x.getId()
                            + "\"><img class=\"img-thumbnail\" src=\"http://img.youtube.com/vi/" + x.getYoutubeId()
                            + "/0.jpg\"></a><br/><h4 class=\"text-center\">"
                            + x.getTitle()
                            + "</h4><h4 class=\"text-center\"><i>"
                            + x.getAuthor() + "</i></h4></div>")
                    .collect(Collectors.toList())));
        }

        return super.view(request, model, "home");
    }
}
