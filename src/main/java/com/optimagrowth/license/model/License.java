package com.optimagrowth.license.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "licenses")  // Ensure this matches the table name in your database
public class License extends RepresentationModel<License> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "license_id", unique = true, nullable = false)
	private String licenseId;

	@Column(name = "organization_id", nullable = false)
	private String organizationId;

	@Column(name = "description")
	private String description;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "license_type")
	private String licenseType;

	// Default constructor (Required for JPA)
	public License() {}

	// Getters and Setters for all fields
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getLicenseId() { return licenseId; }
	public void setLicenseId(String licenseId) { this.licenseId = licenseId; }

	public String getOrganizationId() { return organizationId; }
	public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getProductName() { return productName; }
	public void setProductName(String productName) { this.productName = productName; }

	public String getLicenseType() { return licenseType; }
	public void setLicenseType(String licenseType) { this.licenseType = licenseType; }
}
