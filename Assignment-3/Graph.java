import java.util.List;

public class Graph {

    private final List<CreateVertex> cities; /** Contains the list of vertices **/
    private final List<CreateEdge> routes; /** Contains the list of nodes **/

    // Takes the cities and routes as input
    public Graph(List<CreateVertex> vertexes, List<CreateEdge> edges) {
        this.cities = vertexes;
        this.routes = edges;
    }

    public List<CreateVertex> getCities() {
        return cities;
    }

    public List<CreateEdge> getRoutes() {
        return routes;
    }
}
