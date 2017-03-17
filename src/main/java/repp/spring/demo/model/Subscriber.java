package repp.spring.demo.model;

public class Subscriber {
    private final Long id;
    private final String name;

    public Subscriber(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
