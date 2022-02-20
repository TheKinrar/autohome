package fr.thekinrar.autohome;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Group {
    private final String name;
    private final List<Device> devices;

    public Group(String name, List<Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public boolean has(Device device) {
        return devices.contains(device);
    }

    public <T extends Device> Stream<T> stream(Class<T> clazz) {
        return devices.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    public <T extends Device> boolean anyMatch(Class<T> clazz, Predicate<T> predicate) {
        return stream(clazz).anyMatch(predicate);
    }

    public <T extends Device> boolean noneMatch(Class<T> clazz, Predicate<T> predicate) {
        return stream(clazz).noneMatch(predicate);
    }

    public <T extends Device> void forEach(Class<T> clazz, Consumer<T> consumer) {
        stream(clazz).forEach(consumer);
    }

    public Group union(Group other) {
        var u = new ArrayList<>(devices);
        u.addAll(other.devices);
        return new Group(name + " U " + other.name, u);
    }
}
