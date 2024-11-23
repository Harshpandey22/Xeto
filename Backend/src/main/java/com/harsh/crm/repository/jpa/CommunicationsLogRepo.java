package com.harsh.crm.repository.jpa;

import com.harsh.crm.dto.CommunicationLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationsLogRepo extends JpaRepository<CommunicationLogs, Long> {

}
