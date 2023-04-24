package sm.services;

import sm.model.Competition;
import sm.model.Participant;

public interface ISwimMasterObserver {
    // registration is an observable action of the app
    // possible form of the call - must research further !!!
    void participantRegistered(Participant participant, Iterable<Competition> competitions);
}
