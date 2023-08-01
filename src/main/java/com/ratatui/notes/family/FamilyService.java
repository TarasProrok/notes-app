package com.ratatui.notes.family;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserMapper;
import com.ratatui.notes.user.UserService;
import com.ratatui.notes.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public Family getFamilyByCode(String code){
        Optional<Family> optional = familyRepository.findByCode(code);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }

    public Family getFamilyById(UUID id){
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }


    public void leaveFamily() {
        User currentUser = userService.getCurrentUser();
        currentUser.setFamily(null);
        userService.updateUser(userMapper.mapEntityToDto(currentUser));
    }

    public void addFamily(Family family) {
        User currentUser = userService.getCurrentUser();
        currentUser.setFamily(family);
        userService.updateUser(userMapper.mapEntityToDto(currentUser));
    }

    public FamilyResponseDto createFamily(String title) {
        Family family = Family.builder().title(title).build();
        family.setCode(Helper.getRandomString(5));
        familyRepository.save(family);
        return familyMapper.mapEntityToDto(family);
    }
}
