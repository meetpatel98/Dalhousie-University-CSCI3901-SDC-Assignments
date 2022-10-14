// Manages the creation of city with its description
public class CityDetails {

    private String cityName;
    private boolean testRequired;
    private int timeToTest;
    private int nightlyHotelCost;

    public CityDetails(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) {
        this.cityName = cityName;
        this.testRequired = testRequired;
        this.timeToTest = timeToTest;
        this.nightlyHotelCost = nightlyHotelCost;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isTestRequired() {
        return testRequired;
    }

    public int getTimeToTest() {
        return timeToTest;
    }

    public int getNightlyHotelCost() {
        return nightlyHotelCost;
    }

}
