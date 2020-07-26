package rockwithme.app.service;

import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.binding.BandRemoveMemberBindingDTO;
import rockwithme.app.model.binding.BandRemoveProducerBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;

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

    void removeMember(BandRemoveMemberBindingDTO bandRemoveMemberBindingDTO);

    void addProducer(User user, Band band);

    void removeProducer(BandRemoveProducerBindingDTO bandRemoveProducerBindingDTO);

    void addEvent(Event event, String bandId);

    void addLike(Like like, Band band);

    void addPhoto(String bandId, String imgUrl);

    void deleteBandsWithNoMembers();

    List<Band> findAllToDelete();

    BandOfTheWeekServiceDTO getBandOfTheWeek();
}
