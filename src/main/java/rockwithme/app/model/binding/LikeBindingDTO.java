package rockwithme.app.model.binding;

public class LikeBindingDTO {
    private String username;
    private String bandId;

    public LikeBindingDTO() {
    }

    public LikeBindingDTO(String username, String bandId) {
        this.username = username;
        this.bandId = bandId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }
}
