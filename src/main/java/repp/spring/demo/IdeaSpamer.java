package repp.spring.demo;


import repp.spring.demo.service.IdeaCreationService;

public class IdeaSpamer implements Runnable {

    private IdeaCreationService ideaCreationService;

    public IdeaSpamer(IdeaCreationService ideaCreationService) {
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
            System.out.println("call createIdea, service = " + ideaCreationService.toString());
            int result = ideaCreationService.createIdea("Just one more brilliant idea (" +
                    ideasCount++ +
                    ")");
        }
        System.out.println("Finished run");
    }
}
