package com.ratatui.notes.family;

import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserMapper;
import com.ratatui.notes.user.UserService;
import com.ratatui.notes.utils.Helper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ratatui.notes.utils.Constants.FAMILY_CODE_LENGTH;

@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public Family getFamilyByCode(String code) {
        Optional<Family> optional = familyRepository.findByCode(code);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }

    public Family getFamilyById(UUID id) {
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }

    public void leaveFamily() {
        User currentUser = userService.getCurrentUser();
        userService.deleteUserFamily(currentUser.getUserId());
    }

    public void addFamily(Family family) {
        User currentUser = userService.getCurrentUser();
        currentUser.setFamily(family);
        userService.updateUser(userMapper.mapEntityToDto(currentUser));
    }

    public FamilyResponseDto createFamily(String title) {
        Family family = Family.builder().title(title).build();
        family.setCode(getNewFamilyCode());
        familyRepository.save(family);
        return familyMapper.mapEntityToDto(family);
    }

    public void updateFamily(UUID id, String title) {
        Family familyById = getFamilyById(id);
        familyById.setTitle(title);
        familyRepository.save(familyById);
    }

    private String getNewFamilyCode() {
        String newCode = Helper.getRandomString(FAMILY_CODE_LENGTH);
        try {
            getFamilyByCode(newCode);
        } catch (Exception e) {
            return newCode;
        }
        return getNewFamilyCode();
    }
}
