package is.fistlab.services;

import is.fistlab.database.entities.User;
import is.fistlab.dto.StudyGroupDto;

import java.util.List;

public interface SeqServiceUserProcessing {

    void runSeq(List<StudyGroupDto> studyGroupList, User user);

}
