package corp.pjh.hello_blog_v2.member.service;

import corp.pjh.hello_blog_v2.member.domain.Member;
import corp.pjh.hello_blog_v2.member.dto.MemberDetails;
import corp.pjh.hello_blog_v2.member.execption.MemberExceptionInfo;
import corp.pjh.hello_blog_v2.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MemberExceptionInfo.LOGIN_FAILED.errorMessage()));

        return new MemberDetails(member.getEmail(), member.getPassword());
    }
}
