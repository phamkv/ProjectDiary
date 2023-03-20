package de.phamkv.projectdiary.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    ProfileRepository profileRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : profile.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new User(profile.getUsername(), profile.getPassword(), authorities);

    }

}
