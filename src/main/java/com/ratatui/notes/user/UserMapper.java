package com.ratatui.notes.user;
import com.ratatui.notes.family.FamilyMapper;
import com.ratatui.notes.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper implements Mapper<User, UserDTO> {

    private final FamilyMapper familyMapper;

    @Override
    public UserDTO mapEntityToDto(User source) throws RuntimeException {
        UserDTO target = new UserDTO();
        target.setPassword(source.getPassword());
        target.setId(source.getUserId());
        target.setEmail(source.getEmail());
        target.setNickname(source.getNickname());
        target.setBirthDate(source.getBirthDate());
        target.setGenderId(source.getGenderId());
        target.setEnable(source.isEnable());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setRole(source.getRole());
        target.setFamily(familyMapper.mapEntityToDto(source.getFamily()));
        return target;
    }


    @Override
    public User mapDtoToEntity(UserDTO source) throws RuntimeException {
        User target = new User();
        target.setPassword(source.getPassword());
        target.setUserId(source.getId());
        target.setEmail(source.getEmail());
        target.setNickname(source.getNickname());
        target.setBirthDate(source.getBirthDate());
        target.setGenderId(source.getGenderId());
        target.setEnable(source.isEnable());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setRole(source.getRole());
        target.setFamily(familyMapper.mapDtoToEntity(source.getFamily()));
        return target;
    }
}
