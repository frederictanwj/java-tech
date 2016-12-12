package springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingMVC {

	@RequestMapping("/greetingMVC")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="greetMVC") String name, Model model) {
		model.addAttribute("name", name);
		return "greetyou";
	}
}
