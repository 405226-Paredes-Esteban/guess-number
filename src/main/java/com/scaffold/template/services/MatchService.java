package com.scaffold.template.services;

import com.scaffold.template.models.Match;
import com.scaffold.template.models.MatchDifficulty;
import com.scaffold.template.models.RoundMatch;
import com.scaffold.template.models.User;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
    Match createMatch(User user, MatchDifficulty match);
    Match getMatchById(Long id);
    RoundMatch playMatch(Match match, Integer number);
}
