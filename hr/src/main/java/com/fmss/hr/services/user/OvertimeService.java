package com.fmss.hr.services.user;

import com.fmss.hr.dto.OvertimeDto;
import com.fmss.hr.entities.Overtime;

import java.time.LocalDateTime;
import java.util.List;

public interface OvertimeService {
    List<OvertimeDto> getAllOvertimeByUserId(Long userId);
    List<Overtime> getAllOvertimes();
    void addOvertime(OvertimeDto overtimeDto);
    void deleteOvertime(Long id);
    List<OvertimeDto> getPendingOvertimesByManagerId(Long managerId);
    OvertimeDto approveOvertime(Long id);
    OvertimeDto rejectOvertime(Long id);

}
