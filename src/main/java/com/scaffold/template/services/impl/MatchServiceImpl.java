package com.scaffold.template.services.impl;

import com.scaffold.template.entities.MatchEntity;
import com.scaffold.template.entities.UserEntity;
import com.scaffold.template.models.*;
import com.scaffold.template.repositories.MatchRepository;
import com.scaffold.template.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Qualifier("moddelMapper")
    @Autowired
    private ModelMapper modelMapper;

    private final Random random = new Random();

    @Override
    public Match createMatch(User user, MatchDifficulty difficulty) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setUserEntity(modelMapper.map(user, UserEntity.class));
        matchEntity.setDifficulty(difficulty);
        switch (matchEntity.getDifficulty()) {
            case HARD : matchEntity.setRemainingTries(5);
            case MEDIUM : matchEntity.setRemainingTries(8);
            case EASY: matchEntity.setRemainingTries(10);
        }
        matchEntity.setNumberToGuess(random.nextInt(100));
        matchEntity.setStatus(MatchStatus.PLAYING);
        matchEntity.setCreatedAt(LocalDateTime.now());
        matchEntity.setUpdatedAt(LocalDateTime.now());
        MatchEntity savedEntity = matchRepository.save(matchEntity);
        return modelMapper.map(savedEntity, Match.class);
    }

    @Override
    public Match getMatchById(Long id) {
        Optional<MatchEntity> matchEntity = matchRepository.findById(id);
        if (matchEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        else {
            return modelMapper.map(matchEntity.get(), Match.class);
        }
    }

    @Override
    public RoundMatch playMatch(Match match, Integer number) {
        RoundMatch roundMatch = new RoundMatch();
        roundMatch.setMatch(match);
        if(match.getStatus().equals(MatchStatus.FINISHED)){
            throw new Error("Match is finished");
        }
        if(match.getNumberToGuess().equals(number)){
            //calcular score
            roundMatch.setRespuesta("GANO!");
        }
        else{
            match.setRemainingTries(match.getRemainingTries()-1);
            if(match.getRemainingTries().equals(0)){
                match.setStatus(MatchStatus.FINISHED);
                roundMatch.setRespuesta("PERDIO!");
            }
            else{
                if(number>match.getNumberToGuess()){
                    roundMatch.setRespuesta("MENOR!");
                }
                else{
                    roundMatch.setRespuesta("MAYOR!");
                }
            }
        }
        UserEntity userEntity = modelMapper.map(match.getUser(), UserEntity.class);
        MatchEntity matchEntity = modelMapper.map(match, MatchEntity.class);
        matchEntity.setUserEntity(userEntity);
        matchEntity.setUpdatedAt(LocalDateTime.now());
        matchRepository.save(matchEntity);
        return roundMatch;
    }
}