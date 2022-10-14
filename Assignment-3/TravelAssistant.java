import java.util.*;

public class TravelAssistant {

    private int cityId = 0; /** id for each city **/
    private int routeId = 0; /**  id for each route **/
    private int id = 0; /** id for each route created by flight or train **/
    private final int hop = 1 /** hop remains constant as hop means travelling from one city to another **/;
    private String modeOfTransportation; /** used to store the mode of transportation for flight and train **/
    private final List<CityDetails> cityDetails; /** stores the cities with their description**/
    private final List<RouteDetails> routeDetails; /** stores the routes with their description**/
    private List<Integer> idList; /** contain the list of ids of the routes which are used in final route **/
    private final List<CreateEdge> routes; /** contains the list of routes from one city to another city **/
    private final List<CreateVertex> cities; /** contains the list of cities **/
    private final List<CreateEdge> finalRouteList; /** contains the final shortest path **/

    /** Constructor **/
    TravelAssistant(){
        cityDetails = new LinkedList<>();
        routeDetails = new LinkedList<>();
        routes = new LinkedList<>();
        cities = new LinkedList<>();
        finalRouteList = new LinkedList<>();
        idList = new LinkedList<>();
    }

    /** this method is used to add the city with its description. It will return false if the
      information contradicts with the information other wise it will return true.
     In this the city name must be unique, testRequired is true if someone who is unvaccinated requires a negative covid test to
     travel into the city, timeToTest is the number of days you will spend in hotel waiting for test
     results and the nightlyHotelCost is the cost that you spend for one night in this city.**/
    public boolean addCity(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) throws IllegalArgumentException{
        try {
            if (cityName.trim().isEmpty() || cityName.equalsIgnoreCase("null")
                    || nightlyHotelCost <= 0) {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
            return false;
        }
        boolean cityExists = isCityExists(cityName);
        if (cityExists){
            System.out.println(cityName+" already known by the TravelAssistant");
        } else {
            this.cityDetails.add(new CityDetails(cityName, testRequired, timeToTest, nightlyHotelCost*timeToTest));
            CreateVertex cityNode = new CreateVertex(cityId, cityName);
            cities.add(cityNode);
            cityId++;
            System.out.println("City Added Successfully");
            return true;
        }
        return false;
    }


    /** this method create a route from startCity to destinationCity. Flighttime is in min and
     flightCost is the cost of flight. It will throws an exceptions snd return false if startCity od destinationCit is
     empty or null. It will also return false if flightTime and flightCode is 0 or negative.
     This method will create a route and store it in list.**/
    public boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost) throws IllegalArgumentException{
        try {
            if (startCity.trim().isEmpty() || startCity.equalsIgnoreCase("null") ||
                    destinationCity.trim().isEmpty() || destinationCity.equalsIgnoreCase("null")  ||
                    flightTime <= 0 || flightCost <= 0) {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
            return false;
        }
        modeOfTransportation = "fly";
        if (isExists(startCity, destinationCity)){
            System.out.println("Route from "+startCity+" to "+destinationCity+" already known by the TravelAssistant");
        } else if (!isCityExists(startCity) && !isCityExists(destinationCity)){
            System.out.println("Start City - "+startCity+ " & Destination City - "+destinationCity+" are not known by the TravelAssistant");
        }else if (!isCityExists(startCity)){
            System.out.println("Start City - "+startCity+ " is not known by the TravelAssistant");
        }else if (!isCityExists(destinationCity)){
            System.out.println("Destination - "+destinationCity+ " is not known by the TravelAssistant");
        }
        else {
            int i=0, j=0;
            int getStartCityId = startCityId(startCity);
            int getDestinationId = destinationId(destinationCity);
            for (i=0; i< cities.size() && getStartCityId != cities.get(i).getCityID(); i++){}
            for (j=0; j< cities.size() && getDestinationId != cities.get(j).getCityID(); j++){}
            CreateEdge route = new CreateEdge(routeId, cities.get(i), cities.get(j), flightTime, flightCost, hop, modeOfTransportation);
            routes.add(route);
            routeId++;
            this.routeDetails.add(new RouteDetails(id, startCity, destinationCity, flightTime, flightCost, hop, modeOfTransportation));
            id++;
            System.out.println("Flight Details Added Successfully");
            return true;
        }
        return false;
    }

    /** this method create a route from startCity to destinationCity. trainTime is in min and
     trainCost is the cost of train. It will throws an exceptions and return false if startCity od destinationCit is
     empty or null. It will also return false if trainTime and trainCode is 0 or negative.
     This method will create a route and store it in list.**/
    public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost) throws IllegalArgumentException{
        try {
            if (startCity.trim().isEmpty() || startCity.equalsIgnoreCase("null") ||
                    destinationCity.trim().isEmpty() || destinationCity.equalsIgnoreCase("null")  ||
                    trainTime <= 0 || trainCost <= 0) {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
            return false;
        }
        modeOfTransportation = "train";
        if (isExists(startCity, destinationCity)){
            System.out.println("Route from "+startCity+" to "+destinationCity+" already known by the TravelAssistant");
        } else if (!isCityExists(startCity) && !isCityExists(destinationCity)){
            System.out.println("Start City - "+startCity+ " & Destination City - "+destinationCity+" are not known by the TravelAssistant");
        }else if (!isCityExists(startCity)){
            System.out.println("Start City - "+startCity+ " is not known by the TravelAssistant");
        }else if (!isCityExists(destinationCity)){
            System.out.println("Destination City - "+destinationCity+ " is not known by the TravelAssistant");
        } else {
            int i=0, j=0;
            int getStartCityId = startCityId(startCity);
            int getDestinationId = destinationId(destinationCity);
            for (i=0; i< cities.size() && getStartCityId != cities.get(i).getCityID(); i++){}
            for (j=0; j< cities.size() && getDestinationId != cities.get(j).getCityID(); j++){}
            CreateEdge route = new CreateEdge(routeId, cities.get(i), cities.get(j), trainTime, trainCost, hop, modeOfTransportation);
            routes.add(route);
            routeId++;
            this.routeDetails.add(new RouteDetails(id, startCity, destinationCity, trainTime, trainCost, hop, modeOfTransportation));
            id++;
            System.out.println("Train Details Added Successfully");
            return true;
        }
        return false;
    }

    /** this method will retrun list consists of the mode of travel (start, fly, train), a space, and then the
     city at the end of the flight or train ride. It will throw exception and return null if
     startCity or destination is empty or null. It will also throw exception and return null if the any of the importance is
     negative. **/
    public List<String> planTrip (String startCity, String destinationCity,  boolean isVaccinated,  int
            costImportance, int travelTimeImportance, int travelHopImportance) throws
            IllegalArgumentException{
        try {
            if (startCity.trim().isEmpty() || startCity.equalsIgnoreCase("null") ||
                    destinationCity.trim().isEmpty() || destinationCity.equalsIgnoreCase("null")  ||
                    costImportance < 0 || travelTimeImportance < 0 || travelHopImportance <0 )  {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
            return null;
        }

        StringBuffer sb = new StringBuffer(); /** Stores the String in specific format **/
        StringBuffer singleCity = new StringBuffer(); /** Used to append the String who has startCity and destinationCity id same**/
        List<String> singleCityList = new LinkedList<>();

        if (!isCityExists(startCity) || !isCityExists(destinationCity)){
            System.out.println("There is no route from "+startCity+ " to "+destinationCity);
            return null;
        }
        else {

            if(startCity.equalsIgnoreCase(destinationCity)){
                singleCity.append("Start ");
                singleCity.append(startCity);
                singleCityList.add(singleCity.toString());
                return singleCityList;
            }

            /** Graph is created using cities and routes**/
            Graph createGraph = new Graph(cities, routes);

            /**Pass the graph as an argument in the Dijkstra Algorithm to find the shorted route **/
            DijkstraAlgo dijkstraAlgo = new DijkstraAlgo(createGraph);
            int getStartCityId = startCityId(startCity);
            int getDestinationId = destinationId(destinationCity);

            List<String> route = dijkstraAlgo.findRoute(cities.get(getStartCityId), costImportance, travelTimeImportance, travelHopImportance, getStartCityId, getDestinationId);
            List<CreateVertex> finalRoute = dijkstraAlgo.getFinalRoute();
            idList = dijkstraAlgo.getIList();
            List<String> routeList = new LinkedList<>();
            Map<Integer, String> cityTransportationType = new HashMap<>();

            for (int y: idList){
                cityTransportationType.put(y, routes.get(y).getModeOfTransportation());
            }

            for (int q=0; q<finalRoute.size(); q++){
                if (q == 0){
                    sb.append("Start ");
                    sb.append(route.get(q));
                    routeList.add(sb.toString());
                }
                else {
                    sb.append(cityTransportationType.get(idList.get(q-1)));
                    sb.append(" ").append(route.get(q));
                    routeList.add(sb.toString());
                }
                sb.delete(0, sb.length());
            }
            return routeList;
        }
    }

    /** returns the id of given city **/
    private int destinationId(String destinationCity){
        for (int i=0; i<cityDetails.size(); i++){
            if (destinationCity.equalsIgnoreCase(cityDetails.get(i).getCityName())){
                return i;
            }
        }
        return -1;
    }

    /** returns the id of given city **/
    private int startCityId(String startCity){
        for (int i=0; i<cityDetails.size(); i++){
            if (startCity.equalsIgnoreCase(cityDetails.get(i).getCityName())){
                return i;
            }
        }
        return -1;
    }

    /** checks if the city is already present or not. If the city is present then it will
     return true else retrun false. **/
    private boolean isCityExists(String cityName){

        for (int i=0; i<cityDetails.size(); i++){
            if (cityDetails.get(i).getCityName().equalsIgnoreCase(cityName)){
                return true;
            }
        }
        return false;
    }

    /** checks if the route is already present or not. If the route is present then it will
     return true else retrun false. **/
    private boolean isExists(String startCity, String destinationCity){
        for (int i=0; i<routeDetails.size(); i++){
            if (routeDetails.get(i).getStartCity().equalsIgnoreCase(startCity) &&
            routeDetails.get(i).getDestinationCity().equalsIgnoreCase(destinationCity)){
                return true;
            }
        }
        return false;
    }
}