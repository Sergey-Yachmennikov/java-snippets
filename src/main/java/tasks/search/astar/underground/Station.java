package tasks.search.astar.underground;

import tasks.search.astar.GraphNode;

import java.util.StringJoiner;


public record Station(String id, String name, double latitude, double longitude) implements GraphNode {

    @Override
    public String toString() {
        return new StringJoiner(", ", Station.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("name='" + name + "'").add("latitude=" + latitude).add("longitude=" + longitude).toString();
    }
}

