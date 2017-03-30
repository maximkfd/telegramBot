package repp.spring.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repp.spring.demo.service.IdeaCreationService;

public class IdeaSpamer implements Runnable {

    Logger log = LoggerFactory.getLogger(Application.class);

    private IdeaCreationService ideaCreationService;

    IdeaSpamer(IdeaCreationService ideaCreationService) {
        this.ideaCreationService = ideaCreationService;
    }

    private int ideasCount = 0;

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ideaCreationService.createIdea("Just one more brilliant idea (" +
                    ideasCount++ +
                    ")");
        }
    }
}
