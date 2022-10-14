public class CreateVertex {

    private int cityID;
    private String cityName;

    public CreateVertex(int cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }
}
