package rockwithme.app.model.binding;

public class BandRemoveProducerBindingDTO {
    private String BandId;
    private String producerUsername;

    public BandRemoveProducerBindingDTO() {
    }

    public String getBandId() {
        return BandId;
    }

    public void setBandId(String bandId) {
        BandId = bandId;
    }

    public String getProducerUsername() {
        return producerUsername;
    }

    public void setProducerUsername(String producerUsername) {
        this.producerUsername = producerUsername;
    }
}
