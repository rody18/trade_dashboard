package dashboard.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import dashboard.data.models.Stop;
import dashboard.data.repositories.StopRepository;
import dashboard.data.repositories.TickerRepository;
import dashboard.services.TdaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/stops/")
public class StopController {

	private final StopRepository stopRepo;
	private final TickerRepository tickerRepo;
	private final TdaService tda;

	@Autowired
	public StopController(StopRepository stopRepo, TickerRepository tickerRepo, TdaService tda) {
		this.stopRepo = stopRepo;
		this.tickerRepo = tickerRepo;
		this.tda = tda;
	}

	@GetMapping("list")
	public String showStopList(Model model) {
		model.addAttribute("stops", stopRepo.findAll());
		return "stops_view";
	}
	
	@RequestMapping("delete/{id}")
	@ResponseBody
	public RedirectView  deleteStop(@PathVariable Long id) {
		log.info("Deleting stop id: " + id);
		try {
		stopRepo.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new RedirectView("/stops/list");
	}
	
    @GetMapping("add")
    public String addStop(Stop stop, Model model) {
    	stop.setStp_status("OK");
        model.addAttribute("tickers", tickerRepo.findAll());
        return "add_stop";
    }
    
    @PostMapping("add")
    public String addStop(@Valid Stop stop, BindingResult result, Model model) {
    	stop.setStp_status("OK");
        if (result.hasErrors()) {
        	model.addAttribute("tickers", tickerRepo.findAll());
            return "add_stop";
        }
        try {
        	stopRepo.save(stop);
        } catch (Exception e) {
        	model.addAttribute("errorMessage",e.getMessage());
        	model.addAttribute("tickers", tickerRepo.findAll());
        	return "add_stop";
        }
        return "redirect:list";
    }
    
    @GetMapping("edit/{id}")
    public String updateStop(@PathVariable("id") long id, Model model) {
        Stop stp = stopRepo.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid Stop Id:" + id));
        
        model.addAttribute("stop", stp);
        model.addAttribute("tickers", tickerRepo.findAll());
        return "edit_stop";
    }
    
	@PostMapping("edit/{id}") 
	public String updateStop(@PathVariable("id") long id, @Valid Stop stop, BindingResult result, Model model) { 
		if (result.hasErrors()) { 
			  stop.setStp_id(id);
			  model.addAttribute("tickers", tickerRepo.findAll());
			  return "edit_stop"; 
		}
	    try {
	    	  stop.setStp_id(id);
	    	  stop.setStp_status("OK");
	          stopRepo.save(stop);
	    } catch (Exception e) {
			model.addAttribute("errorMessage",e.getMessage());
			model.addAttribute("tickers", tickerRepo.findAll());
			return "edit_stop";
	    }
		return "redirect:/stops/list"; 
	}
	
	@RequestMapping("sync")
    public String syncStops() {
    	tda.syncStops();
        return "redirect:list";
    }
	
	@RequestMapping("execute/{id}")
    public RedirectView execStop(@PathVariable Long id) {
		Optional<Stop> stp = stopRepo.findById(id);
		if (stp.isPresent()) {
			tda.execStop(stp.get());
		}
		return new RedirectView("/stops/list");
    }
}
