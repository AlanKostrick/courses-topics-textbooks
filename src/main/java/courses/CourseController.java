package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CourseController {

	@Resource
	CourseRepository courseRepo;

	@Resource
	TopicRepository topicRepo;

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll());
		return "courses";
	}

	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value = "id") Long id, Model model) {

		Optional<Course> course = courseRepo.findById(id);

		if (course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course";
		}

		throw new CourseNotFoundException();

	}

	@RequestMapping("/topics")
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
		return "topics";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id") Long id, Model model) {
		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic";
		}

		throw new TopicNotFoundException();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class CourseNotFoundException extends RuntimeException {
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class TopicNotFoundException extends RuntimeException {
	}

}
