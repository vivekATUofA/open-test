package com.optimagrowth.license.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
public class LicenseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LicenseService licenseService;

    @InjectMocks
    private LicenseController licenseController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenseController).build();
    }

    @Test
    void testGetLicense_Success() throws Exception {
        License license = new License();
        license.setLicenseId("1001");
        license.setOrganizationId("1");
        license.setProductName("Software X");

        when(licenseService.getLicense("1001", "1")).thenReturn(license);

        mockMvc.perform(get("/v1/organization/1/license/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value("1001"))
                .andExpect(jsonPath("$.organizationId").value("1"))
                .andExpect(jsonPath("$.productName").value("Software X"));

        verify(licenseService, times(1)).getLicense("1001", "1");
    }

    @Test
    void testGetLicense_NotFound() throws Exception {
        when(licenseService.getLicense("9999", "1")).thenReturn(null);

        mockMvc.perform(get("/v1/organization/1/license/9999"))
                .andExpect(status().isOk()) // Ensure it returns null gracefully
                .andExpect(content().string(""));

        verify(licenseService, times(1)).getLicense("9999", "1");
    }

    @Test
    void testGetLicensesByOrganization() throws Exception {
        License license = new License();
        license.setLicenseId("1001");
        license.setOrganizationId("1");
        license.setProductName("Software X");

        when(licenseService.getLicensesByOrganization("1")).thenReturn(Collections.singletonList(license));

        mockMvc.perform(get("/v1/organization/1/license"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licenseId").value("1001"))
                .andExpect(jsonPath("$[0].organizationId").value("1"))
                .andExpect(jsonPath("$[0].productName").value("Software X"));

        verify(licenseService, times(1)).getLicensesByOrganization("1");
    }

    @Test
    void testCreateLicense() throws Exception {
        License license = new License();
        license.setLicenseId("1001");
        license.setOrganizationId("1");
        license.setProductName("Software X");

        when(licenseService.createLicense(any(License.class), eq("1"), any(Locale.class)))
                .thenReturn("License created successfully with ID 1001");

        mockMvc.perform(post("/v1/organization/1/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"licenseId\": \"1001\", \"organizationId\": \"1\", \"productName\": \"Software X\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("License created successfully with ID 1001"));

        verify(licenseService, times(1)).createLicense(any(License.class), eq("1"), any(Locale.class));
    }

    @Test
    void testUpdateLicense() throws Exception {
        License license = new License();
        license.setLicenseId("1001");
        license.setOrganizationId("1");
        license.setProductName("Updated Software X");

        when(licenseService.updateLicense(any(License.class), eq("1"), any(Locale.class)))
                .thenReturn("License updated successfully");

        mockMvc.perform(put("/v1/organization/1/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"licenseId\": \"1001\", \"organizationId\": \"1\", \"productName\": \"Updated Software X\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("License updated successfully"));

        verify(licenseService, times(1)).updateLicense(any(License.class), eq("1"), any(Locale.class));
    }

    @Test
    void testDeleteLicense() throws Exception {
        when(licenseService.deleteLicense("1001", "1", Locale.ENGLISH)).thenReturn("License deleted successfully");

        mockMvc.perform(delete("/v1/organization/1/license/1001"))
                .andExpect(status().isOk())
                .andExpect(content().string("License deleted successfully"));

        verify(licenseService, times(1)).deleteLicense("1001", "1", Locale.ENGLISH);
    }

    @Test
    void testDeleteLicense_NotFound() throws Exception {
        when(licenseService.deleteLicense("9999", "1", Locale.ENGLISH)).thenReturn("License not found");

        mockMvc.perform(delete("/v1/organization/1/license/9999"))
                .andExpect(status().isOk())
                .andExpect(content().string("License not found"));

        verify(licenseService, times(1)).deleteLicense("9999", "1", Locale.ENGLISH);
    }
}
