package rockwithme.app.service;

import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.JoinRequest;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.entity.User;
import rockwithme.app.model.service.BandDetailsDTO;
import rockwithme.app.model.service.BandMyAllBandsDTO;
import rockwithme.app.model.service.BandMyBandDetailsDTO;
import rockwithme.app.model.service.BandServiceDTO;

import java.util.List;
import java.util.Set;

public interface BandService {
    Band registerBand(BandRegisterDTO band);

    List<BandServiceDTO> getAllBands();

    BandDetailsDTO getBandDetails(String id);

    BandMyBandDetailsDTO getMyBandDetails(String id);

    Band getBandById(String id);

    Set<BandMyAllBandsDTO> getBandByMember(String username);

    void addRequest(Band band, JoinRequest request);

    void addMember(Band band, PlayerSkills playerSkills);

    void addProducer(User user, Band band);

}
