package com.fmss.hr.services.user.impl;

import com.fmss.hr.dto.TimeSheetDto;
import com.fmss.hr.dto.request.SheetDetailRequest;
import com.fmss.hr.dto.request.SheetUpdate;
import com.fmss.hr.entities.SheetDetails;
import com.fmss.hr.entities.Task;
import com.fmss.hr.entities.TimeSheet;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.TimeSheetMapper;
import com.fmss.hr.repos.user.SheetDetailsRepository;
import com.fmss.hr.repos.user.TimeSheetRepository;
import com.fmss.hr.repos.user.UserRepository;
import com.fmss.hr.services.user.TimeSheetService;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Qualifier("Time sheet services")
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final UserService userService;
    private final TimeSheetMapper timeSheetMapper;
    private final TaskServiceImpl taskService;
    private final SheetDetailsRepository sheetDetailsRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean createTimeSheet(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null)
            return false;

        LocalDate date = LocalDate.now();

        List<TimeSheet> userSheet = user.getTimeSheet();

        if (userSheet == null) {
            userSheet = new ArrayList<>();
        }

        Map<Integer, String> months = arrangeMonths();

        int day = 1;

        LocalDate customDate = LocalDate.of(date.getYear(), date.getMonthValue(), day);

        while (day <= date.lengthOfMonth()) {
            TimeSheet timeSheet = new TimeSheet();

            customDate = LocalDate.of(date.getYear(), date.getMonthValue(), day);

            timeSheet.setDay(customDate.getDayOfMonth());
            timeSheet.setMonth(months.get(customDate.getMonthValue()));
            timeSheet.setYear(customDate.getYear());
            timeSheet.setUser(user);
            timeSheet.setIsFilled(false);
            timeSheet.setIsHoliday(customDate.getDayOfWeek().toString().equals("SATURDAY") || customDate.getDayOfWeek().toString().equals("SUNDAY"));

            timeSheetRepository.save(timeSheet);
            userSheet.add(timeSheet);

            day++;
        }
        user.setTimeSheet(userSheet);
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean updateTimeSheet(SheetUpdate sheet, Long userId, Long sheetId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return false;
        }

        TimeSheet timeSheet = timeSheetRepository.findById(sheetId).orElse(null);

        if (timeSheet == null) {
            return false;
        }

        Task task = taskService.getTaskByTitleAndUserId(sheet.getTaskTitle(), userId);

        if (task == null) {
            return false;
        }

        timeSheet.setMonth(sheet.getMonth());
        timeSheet.setTimeSpent(sheet.getTimeSpent());
        timeSheet.setYear(sheet.getYear());
        timeSheet.setDay(sheet.getDay());

        timeSheet.setContent(sheet.getContent());
        timeSheet.setIsFilled(true);

        timeSheetRepository.save(timeSheet);

        return true;
    }

    @Override
    public Boolean deleteTimeSheet() {
        return null;
    }

    @Override
    public Map<Integer, String> arrangeMonths() {
        Map<Integer, String> months = new HashMap<Integer, String>();

        months.put(1, "Ocak");
        months.put(2, "Şubat");
        months.put(3, "Mart");
        months.put(4, "Nisan");
        months.put(5, "Mayıs");
        months.put(6, "Haziran");
        months.put(7, "Temmuz");
        months.put(8, "Ağustos");
        months.put(9, "Eylül");
        months.put(10, "Ekim");
        months.put(11, "Kasım");
        months.put(12, "Aralık");

        return months;
    }

    public Map<String, Integer> arrangeMonthsByString() {
        Map<String, Integer> months = new HashMap<String, Integer>();

        months.put("Ocak", 1);
        months.put("Şubat", 2);
        months.put("Mart", 3);
        months.put("Nisan", 4);
        months.put("Mayıs", 5);
        months.put("Haziran", 6);
        months.put("Temmuz", 7);
        months.put("Ağustos", 8);
        months.put("Eylül", 9);
        months.put("Ekim", 10);
        months.put("Kasım", 11);
        months.put("Aralık", 12);

        return months;
    }


    @Override
    public Boolean checkTimeSheet(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null)
            return null;

        LocalDate date = LocalDate.now();

        Map<Integer, String> months = arrangeMonths();

        int currentMonth = date.getMonthValue();
        int lastSheetMonth = 0;

        List<String> sheetMonths = getMonthsByYearAndUserId(date.getYear(), userId);

        for (Integer month : months.keySet()) {
            if (sheetMonths.contains(months.get(month))) {
                lastSheetMonth = month;
            }
        }

        int currentYear = date.getYear();

        List<Integer> sheetYears = getYearsByUserId(userId);

        if (!sheetYears.contains(currentYear) || currentMonth != lastSheetMonth) {
            createTimeSheet(userId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkTimeSheetForAll() {
        LocalDate date = LocalDate.now();

        Map<Integer, String> months = arrangeMonths();

        int currentMonth = date.getMonthValue();
        int currentYear = date.getYear();

        List<Long> userIds = userRepository.getAllIdsByRole(2);

        for (Long userId : userIds) {
            int lastSheetMonth = getLastMonth(currentYear, months, userId);

            List<Integer> sheetYears = getYearsByUserId(userId);
            List<String> sheetMonths = getMonthsByYearAndUserId(date.getYear(), userId);

            for (Integer month : months.keySet()) {
                if (sheetMonths.contains(months.get(month))) {
                    lastSheetMonth = month;
                }
            }

            Integer filledNum = timeSheetRepository.getLastMonthDayCountByUser(months.get(lastSheetMonth), userId);

            LocalDate specificDate = LocalDate.of(currentYear, lastSheetMonth, 1);

            if (!sheetYears.contains(currentYear) || currentMonth != lastSheetMonth || filledNum < specificDate.lengthOfMonth()) {
                createTimeSheet(userId);
            }
        }
        return true;
    }

    public int getLastMonth(Integer year, Map<Integer, String> months, Long userId) {
        List<String> monthsByYear = timeSheetRepository.getMonthsByYear(year);

        int lastSheetMonth = 0;
        for (Integer month : months.keySet()) {
            if (monthsByYear.contains(months.get(month))) {
                if (month > lastSheetMonth) {
                    lastSheetMonth = month;
                }
            }
        }

        return lastSheetMonth;
    }

    @Override
    public List<TimeSheetDto> getTimeSheetsByUserAndByYear(Long userId, String month, int year) {
        List<TimeSheet> timeSheets = timeSheetRepository.findAllByUserIdAndMonthAndYearOrderById(userId, month, year);

        return timeSheetMapper.ListToDtoList(timeSheets);
    }

    @Override
    public List<TimeSheetDto> getUsersAndTimeSheetProgress(String month) {
        return null;
    }

    @Transactional
    public int monthlySheetCount(String month, Long userId) {
        return timeSheetRepository.daysOfMonthCountByUserId(month, userId);
    }

    @Transactional
    public List<String> getMonthsByYearAndUserId(int year, Long userId) {
        return timeSheetRepository.getMonthsByYearAndUserId(year, userId);
    }

    @Transactional
    public List<Integer> getYearsByUserId(Long userId) {
        return timeSheetRepository.getYearsByUserId(userId);
    }

    @Transactional
    public Integer getFillCountByMonthAndYear(String month, Integer year, Long userId) {
        return timeSheetRepository.getFillCountByMonthAndYear(month, year, userId);
    }

    public Integer getTimeSpentByMonthAndYear(String month, Integer year, Long userId) {
        Integer hour = timeSheetRepository.getTimeSpentByMonthAndYear(month, year, userId);
        if (hour != null) {
            return hour;
        }
        return 0;
    }

    public Integer getRemainingDaysByMonthAndYear(String month, Integer year, Long userId) {
        return timeSheetRepository.getRemainingDaysByMonthAndYear(month, year, userId);
    }

    public List<Integer> getYears() {
        return timeSheetRepository.getYears();
    }

    public List<String> getMonthsByYear(int year) {
        return timeSheetRepository.getMonthsByYear(year);
    }

    public List<SheetDetails> getSheetDetailsById(Long sheetId) {
        return sheetDetailsRepository.findAllByTimeSheetId(sheetId);
    }

    @Override
    public Boolean addSheetDetail(SheetDetailRequest sheetDetailRequest) {
        TimeSheet timeSheet = getSheetById(sheetDetailRequest.getSheetId());

        if (timeSheet == null) {
            return false;
        }

        Task task = taskService.getTaskByTitle(sheetDetailRequest.getTaskTitle());

        if (task == null) {
            return false;
        }

        if (timeSheet.getTimeSpent() + sheetDetailRequest.getTimeSpent() > 24) {
            return false;
        }

        SheetDetails sheetDetails = new SheetDetails();
        sheetDetails.setTimeSheet(timeSheet);
        sheetDetails.setTask(task);
        sheetDetails.setTimeSpent(sheetDetailRequest.getTimeSpent());
        sheetDetails.setContent(sheetDetailRequest.getContent());

        sheetDetailsRepository.save(sheetDetails);

        timeSheet.setTimeSpent(timeSheet.getTimeSpent()+sheetDetails.getTimeSpent());

        timeSheet.setIsFilled(timeSheet.getTimeSpent() >= 8);

        timeSheetRepository.save(timeSheet);

        return true;
    }

    @Override
    public Boolean deleteSheetDetail(Long detailId) {
        SheetDetails sheetDetails = sheetDetailsRepository.findById(detailId).orElse(null);

        assert sheetDetails != null;
        TimeSheet timeSheet = sheetDetails.getTimeSheet();

        timeSheet.setTimeSpent(timeSheet.getTimeSpent()-sheetDetails.getTimeSpent());

        timeSheet.setIsFilled(timeSheet.getTimeSpent() >= 8);

        timeSheetRepository.save(timeSheet);

        sheetDetailsRepository.deleteById(detailId);
        return true;
    }

    @Override
    public Boolean updateSheetDetail(SheetDetailRequest sheetDetailRequest, Long detailId) {
        SheetDetails sheetDetails = sheetDetailsRepository.findById(detailId).orElse(null);

        assert sheetDetails != null;
        int oldTime = sheetDetails.getTimeSpent();

        TimeSheet timeSheet = getSheetById(sheetDetailRequest.getSheetId()); //TODO check

        if (timeSheet == null) {
            return false;
        }

        Task task = taskService.getTaskByTitle(sheetDetailRequest.getTaskTitle());

        if (task == null) {
            return false;
        }

        if (timeSheet.getTimeSpent() + sheetDetails.getTimeSpent() > 24) {
            return false;
        }

        sheetDetails.setTimeSheet(timeSheet);
        sheetDetails.setTask(task);
        sheetDetails.setTimeSpent(sheetDetailRequest.getTimeSpent());
        sheetDetails.setContent(sheetDetailRequest.getContent());

        sheetDetailsRepository.save(sheetDetails);

        timeSheet.setTimeSpent(timeSheet.getTimeSpent()+sheetDetails.getTimeSpent()-oldTime);

        timeSheet.setIsFilled(timeSheet.getTimeSpent() >= 8);

        timeSheetRepository.save(timeSheet);

        return true;
    }

    public TimeSheet getSheetById(Long sheetId) {
        return timeSheetRepository.findById(sheetId).orElse(null);
    }

    public Integer getLengthOfMonth(String month) {
        LocalDate date = LocalDate.now();
        Map<String, Integer> months = arrangeMonthsByString();
        LocalDate customDate = LocalDate.of(date.getYear(), months.get(month), 1);
        return customDate.lengthOfMonth();
    }
    public Boolean hasSheetThatMonth(String month, Long userId) {
        return timeSheetRepository.getLastMonthDayCountByUser(month, userId) != 0;
    }

    public List<User> getUsersWithSheetThatMonth(String month, String fullName) {
        List<User> users = userRepository.findAllByFullNameContainingIgnoreCase(fullName);
        List<User> usersWithSheet = new ArrayList<>();
        for (User user : users) {
            if (Boolean.TRUE.equals(hasSheetThatMonth(month, user.getId()))) {
                usersWithSheet.add(user);
            }
        }
        return usersWithSheet;
    }

    @Override
    public Integer getFillPercentageByMonth(String month, int year, Long userId) {
        return (getFillCountByMonthAndYear(month, year, userId)*100)/getLengthOfMonth(month);
    }
}
