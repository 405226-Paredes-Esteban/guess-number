package com.scaffold.template.services;

import com.scaffold.template.models.Match;
import com.scaffold.template.models.MatchDifficulty;
import com.scaffold.template.models.RoundMatch;
import com.scaffold.template.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User createUser(String userName, String email);
    public Match createUserMatch(Long userId, MatchDifficulty difficulty);
    RoundMatch playUserMatch(Long userId, Long matchId, Integer guess);
}
