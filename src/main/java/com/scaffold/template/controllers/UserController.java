package com.scaffold.template.controllers;


import com.scaffold.template.dtos.*;
import com.scaffold.template.models.Match;
import com.scaffold.template.models.RoundMatch;
import com.scaffold.template.models.User;
import com.scaffold.template.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guess-number/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Qualifier("moddelMapper")
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        User user = userService.createUser(dto.getUserName(), dto.getEmail());
        UserDto dtoCreated = modelMapper.map(user,UserDto.class);
        return ResponseEntity.ok(dtoCreated);
    }

    @PostMapping("/{userId}/matches")
    public ResponseEntity<MatchDto> createUserMatch(@PathVariable Long userId, @RequestBody CreateUserMatchDto createUserMatchDto){
        Match match = userService.createUserMatch(userId, createUserMatchDto.getMatchDifficulty());
        MatchDto matchDto = modelMapper.map(match,MatchDto.class);
        return ResponseEntity.ok(matchDto);
    }

    @PostMapping("/{userId}/matches/{matchId}")
    public ResponseEntity<RoundMatchDto> playUserMatch(@PathVariable Long userId, @PathVariable Long matchId, @RequestBody PlayUserMatchDto playUserMatchDto){
        RoundMatch roundMatch = userService.playUserMatch(userId,matchId,playUserMatchDto.getGuess());
        RoundMatchDto roundMatchDto = modelMapper.map(roundMatch,RoundMatchDto.class);
        MatchDto matchDto = modelMapper.map(roundMatch.getMatch(),MatchDto.class);
        roundMatchDto.setMatchDto(matchDto);
        return ResponseEntity.ok(roundMatchDto);
    }
}
