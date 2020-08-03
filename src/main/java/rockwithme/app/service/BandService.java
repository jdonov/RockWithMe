package rockwithme.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rockwithme.app.model.binding.BandRegisterDTO;
import rockwithme.app.model.binding.BandRemoveMemberBindingDTO;
import rockwithme.app.model.binding.BandRemoveProducerBindingDTO;
import rockwithme.app.model.binding.BandSearchBindingDTO;
import rockwithme.app.model.entity.*;
import rockwithme.app.model.service.*;

import java.util.List;

public interface BandService {
    BandServiceDTO registerBand(BandRegisterDTO band);

    List<BandServiceDTO> getAllBands();

    BandDetailsDTO getBandDetails(String id);

    BandMyBandDetailsDTO getMyBandDetails(String id);

    Band getBandById(String id);

    List<BandMyAllBandsDTO> getBandByMember(String username);

    void addRequest(Band band, JoinRequest request);

    void addMember(Band band, PlayerSkills playerSkills);

    void removeMember(BandRemoveMemberBindingDTO bandRemoveMemberBindingDTO);

    BandServiceDTO addProducer(User user, Band band);

    void removeProducer(BandRemoveProducerBindingDTO bandRemoveProducerBindingDTO);

    void addEvent(Event event, String bandId);

    void addLike(Like like, Band band);

    void addPhoto(String bandId, String imgUrl);

    void deleteBandsWithNoMembers();

    List<Band> findAllToDelete();

    BandOfTheWeekServiceDTO getBandOfTheWeek();

    int getCountOfAllActiveBands();

    int getCountOfAllDeletedBands();

    int getTotalCountOfAllBAnds();

    BandAdminServiceDTO getLastRegistered();

    Page<BandSearchServiceDTO> searchBands(BandSearchBindingDTO bandSearchBindingDTO, Pageable pageable);
}
