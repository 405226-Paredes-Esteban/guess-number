package com.scaffold.template.services.impl;

import com.scaffold.template.entities.UserEntity;
import com.scaffold.template.models.Match;
import com.scaffold.template.models.MatchDifficulty;
import com.scaffold.template.models.RoundMatch;
import com.scaffold.template.models.User;
import com.scaffold.template.repositories.UserRepository;
import com.scaffold.template.services.MatchService;
import com.scaffold.template.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Qualifier("moddelMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MatchService matchService;

    @Override
    public User createUser(@RequestBody String userName, String email) {
        Optional<UserEntity> userEntityOptional = userRepository.getByEmail(email);
        if(userEntityOptional.isPresent()){
            //Generar error
            //throw new EntityExistsException();
            return null;
        }else{
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setEmail(email);
            UserEntity savedUser = userRepository.save(userEntity);
            return modelMapper.map(savedUser, User.class);
        }
    }

    @Override
    public Match createUserMatch(Long userId, MatchDifficulty difficulty) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if(userEntityOptional.isEmpty()){
            //throw new EntityNotFoundException();
            return null;
        }
        else{
            UserEntity userEntity = userEntityOptional.get();
            return matchService.createMatch(modelMapper.map(userEntity, User.class),difficulty);
        }
    }

    @Override
    public RoundMatch playUserMatch(Long userId, Long matchId, Integer guess) {
        Match match = matchService.getMatchById(matchId);
        if(Objects.equals(match.getUser().getId(), userId)){
            return matchService.playMatch(match,guess);
        }else{
            throw new IllegalArgumentException();
        }
    }
}
