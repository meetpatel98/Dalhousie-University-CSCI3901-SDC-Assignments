public class RouteDetails {

    private String startCity; // used to store the route start city
    private String destinationCity; // used to store the routedestination city
    private int time; // used to store the route time
    private int cost; // used to store the route cost
    private int hop; // used to store the route hop
    private int id; // used to store the route id
    private String modeOfTransportation; // used to store the route modeOfTransportation


    public RouteDetails(int id, String startCity, String destinationCity, int time, int cost, int hop, String modeOfTransportation) {
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.time = time;
        this.cost = cost;
        this.hop = hop;
        this.id = id;
        this.modeOfTransportation = modeOfTransportation;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public int getTime() {
        return time;
    }

    public int getCost() {
        return cost;
    }

}
