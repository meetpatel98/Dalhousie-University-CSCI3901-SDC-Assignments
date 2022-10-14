public class CreateEdge {

    private CreateVertex sourceCity;
    private String sourceCity1;
    private CreateVertex destinationCity;
    private String destinationCity1;
    private int cost;
    private String modeOfTransportation;
    private int routeId;
    private int time;
    private int hop;

    public CreateEdge(int routeId, CreateVertex sourceCity, CreateVertex destinationCity, int time, int cost, int hop, String modeOfTransportation) {
        this.routeId = routeId;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.cost = cost;
        this.time = time;
        this.hop = hop;
        this.modeOfTransportation = modeOfTransportation;
    }
    public CreateEdge(String sourceCity1, String destinationCity1) {
        this.sourceCity1 = sourceCity1;
        this.destinationCity1 = destinationCity1;
    }

    public int getTime() {
        return time;
    }

    public int getHop() {
        return hop;
    }

    public String getSourceCity1() {
        return sourceCity1;
    }

    public String getDestinationCity1() {
        return destinationCity1;
    }

    public String getModeOfTransportation() {
        return modeOfTransportation;
    }

    public CreateVertex getSourceCity() {
        return sourceCity;
    }

    public CreateVertex getDestinationCity() {
        return destinationCity;
    }

    public int getCost() {
        return cost;
    }
}
