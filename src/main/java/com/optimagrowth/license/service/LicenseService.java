package com.optimagrowth.license.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	private MessageSource messages;

	public License getLicense(String licenseId, String organizationId) {
		return licenseRepository.findByLicenseId(licenseId).orElse(null);
	}
	public List<License> getLicensesByOrganization(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}



	public String createLicense(License license, String organizationId, Locale locale) {
		if (license == null) {
			return messages.getMessage("license.create.error", null, locale);
		}
		license.setOrganizationId(organizationId);
		License savedLicense = licenseRepository.save(license);
		return String.format(messages.getMessage("license.create.message", null, locale), savedLicense.getLicenseId());
	}

	public String updateLicense(License license, String organizationId, Locale locale) {
		Optional<License> existingLicense = licenseRepository.findByLicenseId(license.getLicenseId());

		if (existingLicense.isPresent()) {
			License updatedLicense = existingLicense.get();
			updatedLicense.setOrganizationId(organizationId);
			updatedLicense.setDescription(license.getDescription());
			updatedLicense.setProductName(license.getProductName());
			updatedLicense.setLicenseType(license.getLicenseType());

			licenseRepository.save(updatedLicense);
			return String.format(messages.getMessage("license.update.message", null, locale), updatedLicense.getLicenseId());
		} else {
			return messages.getMessage("license.update.error.notfound", null, locale);
		}
	}


	public String deleteLicense(String licenseId, String organizationId, Locale locale) {
		Optional<License> license = licenseRepository.findByLicenseId(licenseId);

		if (license.isPresent()) {
			licenseRepository.delete(license.get()); // Use `.get()` to retrieve the License entity
			return String.format(messages.getMessage("license.delete.message", null, locale), licenseId, organizationId);
		}

		return messages.getMessage("license.delete.error", null, locale);
	}




}
