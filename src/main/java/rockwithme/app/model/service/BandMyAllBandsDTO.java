package rockwithme.app.model.service;


public class BandMyAllBandsDTO extends BaseServiceModel{
    private String name;
    private int newRequests;

    public BandMyAllBandsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewRequests() {
        return newRequests;
    }

    public void setNewRequests(int newRequests) {
        this.newRequests = newRequests;
    }
}
