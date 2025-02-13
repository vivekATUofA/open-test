package com.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;

	/**
	 * Get a single license by ID.
	 */
	@GetMapping("/{licenseId}")
	public ResponseEntity<License> getLicense(
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {

		License license = licenseService.getLicense(licenseId, organizationId);

		// Add HATEOAS links
		license.add(
				linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
				linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null)).withRel("createLicense"),
				linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license, null)).withRel("updateLicense"),
				linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId(), null)).withRel("deleteLicense")
		);

		return ResponseEntity.ok(license);
	}

	/**
	 * Get all licenses for an organization.
	 */
	@GetMapping
	public ResponseEntity<List<License>> getLicensesByOrganization(@PathVariable("organizationId") String organizationId) {
		return ResponseEntity.ok(licenseService.getLicensesByOrganization(organizationId));
	}

	/**
	 * Create a new license.
	 */
	@PostMapping
	public ResponseEntity<String> createLicense(
			@PathVariable("organizationId") String organizationId,
			@RequestBody License request,
			@RequestHeader(value = "Accept-Language", required = false, defaultValue = "en") Locale locale) {
		return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
	}

	/**
	 * Update an existing license.
	 */
	@PutMapping
	public ResponseEntity<String> updateLicense(
			@PathVariable("organizationId") String organizationId,
			@RequestBody License request,
			@RequestHeader(value = "Accept-Language", required = false, defaultValue = "en") Locale locale) {
		return ResponseEntity.ok(licenseService.updateLicense(request, organizationId, locale));
	}

	/**
	 * Delete a license by ID.
	 */
	@DeleteMapping("/{licenseId}")
	public ResponseEntity<String> deleteLicense(
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId,
			@RequestHeader(value = "Accept-Language", required = false, defaultValue = "en") Locale locale) {

		if (licenseId == null || organizationId == null) {
			return ResponseEntity.badRequest().body("License ID or Organization ID is missing.");
		}

		return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId, locale));
	}
}
