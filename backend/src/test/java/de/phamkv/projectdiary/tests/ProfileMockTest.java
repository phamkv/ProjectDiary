package de.phamkv.projectdiary.tests;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.phamkv.projectdiary.controller.ProfileController;
import de.phamkv.projectdiary.model.Profile;
import de.phamkv.projectdiary.service.ProfileService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProfileMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    public void getAllProfilesFromService() throws Exception {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("John", "30303"));
        profiles.add(new Profile("Jane", "20001"));
        when(profileService.getAllProfiles()).thenReturn(profiles);
        this.mockMvc.perform(get("/api/profiles")).andExpect(status().isOk())
                .andExpect(content().json("[{\"username\": \"John\", \"password\": \"30303\"}, {\"username\": \"Jane\", \"password\": \"20001\"}]"));
    }

    @Test
    public void getProfileByIdFromService() throws Exception {
        //when(profileService.getProfileById((long) 1)).thenReturn(new Profile("John", "30303"));
        doReturn(new Profile("John", "30303")).when(profileService).getProfileById((long) 1);

        this.mockMvc.perform(get("/api/profiles/1")).andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"John\", \"password\": \"30303\"}"));
    }

    @Test
    public void deleteProfileByIdFromService() throws Exception {
        doNothing().when(profileService).deleteProfile((long) 1);

        this.mockMvc.perform(delete("/api/profiles/1").with(csrf())).andExpect(status().is2xxSuccessful());
    }
}
