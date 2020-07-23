package rockwithme.app.service;

import rockwithme.app.model.binding.JoinRequestBindingDTO;
import rockwithme.app.model.service.JoinRequestServiceDTO;

import java.util.List;

public interface JoinRequestService {
    void submitJoinRequest(JoinRequestBindingDTO joinRequestBindingDTO);

    List<JoinRequestServiceDTO> getRequestByBandId(String id);
    List<JoinRequestServiceDTO> getRequestByUserId(String id);

    void approveRequest(String requestId);

    void rejectRequest(String requestId);
}
