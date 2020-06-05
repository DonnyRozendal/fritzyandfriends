package nl.hva.fritzyandfriends.common;

public enum DeviceType {
    CONSUMER("consumer"),
    PRODUCER("producer"),
    STORAGE("storage");

    private String name;

    DeviceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}