package com.fmss.hr.services.user;

import com.fmss.hr.dto.TimeSheetDto;
import com.fmss.hr.dto.request.SheetDetailRequest;
import com.fmss.hr.dto.request.SheetUpdate;
import com.fmss.hr.entities.SheetDetails;

import java.util.List;
import java.util.Map;

public interface TimeSheetService {
    Boolean createTimeSheet(Long userId);
    Boolean updateTimeSheet(SheetUpdate sheet, Long userId, Long sheetId);
    Boolean deleteTimeSheet();
    Map<Integer, String> arrangeMonths();
    Boolean checkTimeSheet(Long userId);
    List<TimeSheetDto> getTimeSheetsByUserAndByYear(Long userId, String month, int year);
    List<TimeSheetDto> getUsersAndTimeSheetProgress(String month);
    List<SheetDetails> getSheetDetailsById(Long sheetId);
    Boolean addSheetDetail(SheetDetailRequest sheetDetailRequest);
    Boolean deleteSheetDetail(Long detailId);
    Boolean updateSheetDetail(SheetDetailRequest sheetDetailRequest, Long detailId);
    Boolean checkTimeSheetForAll();
    Integer getFillPercentageByMonth(String month, int year, Long userId);
}