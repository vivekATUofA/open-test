package com.optimagrowth.license.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.optimagrowth.license.model.License;
import java.util.List;
import java.util.Optional;

@Repository

public interface LicenseRepository extends JpaRepository<License, Integer> {
    List<License> findByOrganizationId(String organizationId);
    Optional<License> findByLicenseId(String licenseId);

}
