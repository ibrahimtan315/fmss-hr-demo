package com.fmss.hr.mapper;

import com.fmss.hr.dto.VoteDto;
import com.fmss.hr.dto.request.VoteRequest;
import com.fmss.hr.entities.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    Vote toVoteFromVoteRequest(VoteRequest voteRequest);
    VoteDto toVoteDtoFromVote(Vote Vote);
}
