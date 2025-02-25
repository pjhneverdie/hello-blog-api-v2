package corp.pjh.hello_blog_v2.member.service;

import corp.pjh.hello_blog_v2.member.domain.Member;
import corp.pjh.hello_blog_v2.member.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberLoginServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberLoginService memberLoginService;

    @Test
    void loadUserByUsername_성공_테스트() {
        // Given
        String email = "test@test.com";
        String password = "testpassword";

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(new Member(1L, email, password)));

        // When
        UserDetails userDetails = memberLoginService.loadUserByUsername(email);

        // Then
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_실패_테스트() {
        // Given
        String email = "test@test.com";

        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> memberLoginService.loadUserByUsername(email));
    }
}