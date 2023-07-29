package com.ratatui.notes.user;

public class UserMapper implements Mapper <User, UserDTO> {

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
        return target;
    }
}
