import java.util.*;

public class DijkstraAlgo {

    private List<CreateVertex> cities ; /** contains the list of cities **/
    private List<CreateEdge> routes ; /** contains the list of routes from one city to another city **/
    private final List<CreateVertex> visitedCity = new LinkedList<>(); /** Contains the list of cities which is visited**/
    private final List<CreateVertex> notVisitedCity = new LinkedList<>(); /** Contains the list of cities which is not visited **/
    private final Map<CreateVertex, CreateVertex> previousCity = new HashMap<>(); /** contains the pair of city and its predecessors **/
    private final Map<CreateVertex, Integer> cityDistance = new HashMap<>(); /** contains the city and its diatnce **/
    private final List<CreateEdge> finalRouteList = new LinkedList<>();; /** contains the final shortest path **/
    private List<Integer> idList = new LinkedList<>(); /** contain the list of ids of the routes which are used in final route **/

    private List<String> route = new LinkedList<>(); /** contains the final route in string format **/
    private List<CreateVertex> path = new LinkedList<>(); /** contains final shortest path **/
    private List<CreateVertex> finalRoute = new LinkedList<>(); /**  contains the final route from source to destination **/

    // Constructor
    // takes graph as an input
    public DijkstraAlgo(Graph graph) {

        this.cities = new LinkedList<>(graph.getCities());
        this.routes = new LinkedList<>(graph.getRoutes());
    }

    /** this method will return a list of string contain the shortest path from source to destination
      based on the cost importance, travel time Importance and travel hop importance.
     ths method will create an adjacently list to find the shortest path*/
    public List<String> findRoute(CreateVertex source, int costImportance, int travelTimeImportance, int travelHopImportance, int getStartCityId, int getDestinationId) {

        /** initially the distance of source city is set to 0 and **/
        cityDistance.put(source, 0);

        /** initially source is in the set of not visited cities **/
        notVisitedCity.add(source);

        /** The distance of each city from source is set to a high value. This loop will run untill
           the not visited list is empty. In each Iteration the city is selected from the not visited List
         which has lowest distance from startcity **/
        for (int i=0; i<notVisitedCity.size(); i++){
            CreateVertex minimum = null;
            for (CreateVertex city : notVisitedCity) {
                if (minimum == null) {
                    minimum = city;
                } else {
                    int xVertex ;
                    int xMinimum ;
                    Integer dVertex = cityDistance.get(city);
                    if (dVertex == null) {
                        xVertex = Integer.MAX_VALUE;
                    } else {
                        xVertex = dVertex;
                    }
                    Integer dMinimum = cityDistance.get(minimum);
                    if (dMinimum == null) {
                        xMinimum = Integer.MAX_VALUE;
                    } else {
                        xMinimum = dMinimum;
                    }
                    if (xVertex < xMinimum) {
                        minimum = city;
                    }
                }
            }
            CreateVertex node = minimum;
            visitedCity.add(node);
            notVisitedCity.remove(node);

            /** it wiil store the adjacent cities of the particular city in the list.  **/
            List<CreateVertex> adjacentCities = getneighbouringNode(node);
            for (CreateVertex destination : adjacentCities) {
                int xTarget ;
                int xnode ;
                Integer dTarget = cityDistance.get(destination);
                if (dTarget == null) {
                    xTarget = Integer.MAX_VALUE;
                } else {
                    xTarget = dTarget;
                }
                Integer dNode = cityDistance.get(node);
                if (dNode == null) {
                    xnode = Integer.MAX_VALUE;
                } else {
                    xnode = dNode;
                }
                if (xTarget > xnode + nodeDistance(node, destination, costImportance, travelTimeImportance,travelHopImportance)) {
                    cityDistance.put(destination, xnode + nodeDistance(node, destination, costImportance, travelTimeImportance,travelHopImportance));
                    previousCity.put(destination, node);
                    notVisitedCity.add(destination);
                }
            }
        }
        CreateVertex checkReverse = cities.get(getDestinationId);
        if (previousCity.get(checkReverse) == null) {
            return null;
        }
        path.add(checkReverse);
        while (previousCity.get(checkReverse) != null) {
            checkReverse = previousCity.get(checkReverse);
            path.add(checkReverse);
        }
        for(int v = path.size()-1; v>=0; v--){
            finalRoute.add(path.get(v));
        }

        for (CreateVertex vertex : finalRoute) {
            route.add(vertex.getCityName());
        }
        return route;
    }

    /** it wiil return the finalRoute which is shortest path **/
    public List<CreateVertex> getFinalRoute(){
        return finalRoute;
    }

    /** It will return distance between the two node.The distance is calculated
      based on costImportance, travelImportance, travelHopImportance **/
    private int nodeDistance(CreateVertex node1, CreateVertex node2, int costImportance, int travelTimeImportance, int travelHopImportance) {
        for (CreateEdge edge : routes) {
            if ( edge.getSourceCity().equals(node1) && edge.getDestinationCity().equals(node2)) {
                return costImportance * edge.getCost() + travelTimeImportance * edge.getTime() + travelHopImportance * edge.getHop();
            }
        }
        return -1;
    }

    /** it will return list of the neighbouring cities of the provided city **/
    private List<CreateVertex> getneighbouringNode(CreateVertex city) {
        //List to store the neighbouring cities
        List<CreateVertex> nodeNeighbors = new LinkedList<>();

        // finds the neighbouring cities and store it in the list
        for (CreateEdge route : routes) {
            boolean visited = visitedCity.contains(route.getDestinationCity());
            if (!visited  && route.getSourceCity().equals(city)) {
                nodeNeighbors.add(route.getDestinationCity());
            }
        }
        return nodeNeighbors;
    }

    /** Returns the list of indexes of the routes which is present in the finalRoute list.
      this will be useful for getting the mode of transportation of the routes in in the final route**/
    public List<Integer> getIList() {
        int i = 0, j=0;
        for (i=0; i<route.size()-1; i=i+j){
            for (j=1; true; ){
                CreateEdge routeSrcDest = new CreateEdge(route.get(i), route.get(i + j));
                finalRouteList.add(routeSrcDest);
                break;
            }
        }
        for (int x=0; x<routes.size(); x++){
            for (int y=0; y<finalRouteList.size(); y++){
                if (routes.get(x).getSourceCity().getCityName().equals(finalRouteList.get(y).getSourceCity1()) &&
                        routes.get(x).getDestinationCity().getCityName().equals(finalRouteList.get(y).getDestinationCity1())){
                    idList.add(x);
                }
            }
        }
        return idList;
    }
}
