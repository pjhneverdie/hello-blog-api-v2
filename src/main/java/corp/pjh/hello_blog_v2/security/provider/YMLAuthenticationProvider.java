package corp.pjh.hello_blog_v2.security.provider;

import corp.pjh.hello_blog_v2.security.config.AccountConfig;
import corp.pjh.hello_blog_v2.member.dto.MemberDetails;
import corp.pjh.hello_blog_v2.member.execption.MemberExceptionInfo;
import corp.pjh.hello_blog_v2.member.service.MemberLoginService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YMLAuthenticationProvider implements AuthenticationProvider {
    private final AccountConfig accountConfig;
    private final MemberLoginService memberLoginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberDetails memberDetails = (MemberDetails) memberLoginService.loadUserByUsername(authentication.getName());

        return createTokenIfPasswordEquals(memberDetails, (String) authentication.getCredentials())
                .orElseThrow(() -> new UsernameNotFoundException(MemberExceptionInfo.LOGIN_FAILED.errorMessage()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Optional<UsernamePasswordAuthenticationToken> createTokenIfPasswordEquals(MemberDetails memberDetails, String password) {
        if (password.equals(accountConfig.getPassword())) {
            return Optional.of(new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities()));
        }

        return Optional.empty();
    }
}