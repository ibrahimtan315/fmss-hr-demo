package com.fmss.hr.controllers.user;

import com.fmss.hr.dto.OvertimeDto;
import com.fmss.hr.entities.Overtime;
import com.fmss.hr.services.user.OvertimeService;
import com.fmss.hr.services.user.impl.OvertimeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/overtime")
@RequiredArgsConstructor
public class OvertimeController {

    private final OvertimeServiceImpl overtimeService;

    @GetMapping("/allOvertimes")
    public ResponseEntity<List<Overtime>> getOvertimes(){
        List<Overtime> overtime= overtimeService.getAllOvertimes();
        if(overtime!=null){
            return ResponseEntity.status(HttpStatus.OK).body(overtime);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getAllOvertimeById/{userId}")
    public ResponseEntity<List<OvertimeDto>> getOvertimesById(@PathVariable Long userId){
        List<OvertimeDto> overtimeDtoList= overtimeService.getAllOvertimeByUserId(userId);
        if(overtimeDtoList!=null){
            return ResponseEntity.status(HttpStatus.OK).body(overtimeDtoList);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping("/deleteOvertime")
    public void deleteOvertime(Long id){
        overtimeService.deleteOvertime(id);

    }

    @RequestMapping(value = "/addOvertime", method = RequestMethod.POST)
    public ResponseEntity<OvertimeDto> addOvertime(@RequestBody OvertimeDto overtimeDto) {
        overtimeService.addOvertime(overtimeDto);
        return ResponseEntity.ok(overtimeDto);
    }

    @GetMapping(value = "pending-overtime/{managerId}")
    public ResponseEntity<List<OvertimeDto>> getPendingOvertimeByManagerId(@PathVariable("managerId") Long managerId){
        return ResponseEntity.ok(overtimeService.getPendingOvertimesByManagerId(managerId));
    }

    @PutMapping(value = "approve-overtime/{overtimeId}")
    public ResponseEntity<OvertimeDto> approveOvertime(@PathVariable("overtimeId") Long id){
        return ResponseEntity.ok(overtimeService.approveOvertime(id));
    }

    @PutMapping(value = "reject-overtime/{overtimeId}")
    public ResponseEntity<OvertimeDto> rejectOvertime(@PathVariable("overtimeId") Long id){
        return ResponseEntity.ok(overtimeService.rejectOvertime(id));
    }

}

