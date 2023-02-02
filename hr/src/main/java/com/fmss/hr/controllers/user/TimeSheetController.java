package com.fmss.hr.controllers.user;

import com.fmss.hr.dto.TaskDto;
import com.fmss.hr.dto.TimeSheetDto;
import com.fmss.hr.dto.request.SheetDetailRequest;
import com.fmss.hr.dto.request.SheetUpdate;
import com.fmss.hr.dto.request.TaskCreateRequest;
import com.fmss.hr.entities.SheetDetails;
import com.fmss.hr.entities.Task;
import com.fmss.hr.entities.User;
import com.fmss.hr.services.user.TaskService;
import com.fmss.hr.services.user.impl.TimeSheetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timesheets")
@CrossOrigin(origins = "*")
public class TimeSheetController {
    private final TaskService taskService;
    private final TimeSheetServiceImpl timeSheetService;
    @RequestMapping(value ="/createTask/{creatorId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> createTask(@PathVariable Long creatorId, @RequestBody TaskCreateRequest taskCreateRequest) {
        TaskDto task = taskService.createTask(taskCreateRequest, creatorId);

        if (task == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @RequestMapping(value = "/getTask/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getTask(@RequestParam(value="taskTitle") String taskTitle, @PathVariable Long userId) {
        boolean getTask = taskService.getTask(taskTitle, userId);

        if (!getTask)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @RequestMapping(value = "/dropTask/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> dropTask(@RequestParam(value = "taskTitle") String taskTitle, @PathVariable Long userId) {
        Boolean dropTask = taskService.dropTask(taskTitle, userId);
        if (!dropTask)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @RequestMapping(value = "/userTasks/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskDto>> getUserTasks(@PathVariable Long userId) {
        List<TaskDto> taskList = taskService.getUserTasks(userId);
        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @RequestMapping(value = "/updateTask", method = RequestMethod.PUT)
    public ResponseEntity<TaskCreateRequest> updateTask(@RequestParam(value = "taskId") Long taskId, @RequestBody TaskCreateRequest taskCreateRequest) {
        TaskCreateRequest task = taskService.updateTask(taskId, taskCreateRequest);

        if (task == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    /*@RequestMapping(value = "/createSheet/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createTimeSheet(@PathVariable Long userId) {
        if (timeSheetService.createTimeSheet(userId))//TODO buraların geri dönüşü değişebili
            return ResponseEntity.status(HttpStatus.OK).body(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }*/

    @RequestMapping(value = "/updateSheet/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateSheet(@RequestBody SheetUpdate sheet, @PathVariable Long userId, @RequestParam(value = "sheetId") Long sheetId) {
        if (timeSheetService.updateTimeSheet(sheet, userId, sheetId))
            return ResponseEntity.status(HttpStatus.OK).body(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<TimeSheetDto>> getTimeSheetsByUserAndByYear(@PathVariable Long userId, @RequestParam(value = "year") int year, @RequestParam(value = "month") String month) {
        List<TimeSheetDto> timeSheetDtoList = timeSheetService.getTimeSheetsByUserAndByYear(userId, month, year);
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetDtoList);
    }

    @RequestMapping(value="/getSheetCount/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getSheetCountByUserId(@PathVariable Long userId, @RequestParam(value = "month") String month) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.monthlySheetCount(month, userId));
    }

    @RequestMapping(value = "/getMonths/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getMonthsByYearAndUserId(@PathVariable Long userId, @RequestParam(value = "year") int year) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getMonthsByYearAndUserId(year, userId));
    }

    @RequestMapping(value = "/getYears/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getYearsByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getYearsByUserId(userId));
    }

    @RequestMapping(value = "/getFillCount/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getFillCount(@PathVariable Long userId, @RequestParam(value = "month") String month, @RequestParam(value = "year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getFillCountByMonthAndYear(month, year, userId));
    }

    @RequestMapping(value = "/getTotalTaskCompleted/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getTotalTaskCompleted(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTotalTaskCompletedByUserId(userId));
    }

    @RequestMapping(value = "/getUncompletedTasks/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUncompletedTasks(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getUncompletedTasksByUserId(userId));
    }

    @RequestMapping(value = "/getRemainingDays/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getRemainingDays(@PathVariable Long userId, @RequestParam("month") String month, @RequestParam("year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getRemainingDaysByMonthAndYear(month, year, userId));
    }

    @RequestMapping(value = "/getTimeSpent/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getTimeSpentByMonth(@PathVariable Long userId, @RequestParam("month") String month, @RequestParam("year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getTimeSpentByMonthAndYear(month, year, userId));
    }

    @RequestMapping(value = "/getYears", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getYears() {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getYears());
    }

    @RequestMapping(value = "/getMonthsByYear", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getMonthsByYear(@RequestParam(value = "year") int year) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getMonthsByYear(year));
    }

    @RequestMapping(value = "/getSheetDetails", method = RequestMethod.GET)
    public ResponseEntity<List<SheetDetails>> getSheetDetailsBySheetId(@RequestParam("sheetId") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getSheetDetailsById(id));
    }

    @RequestMapping(value = "/addSheetDetail", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addSheetDetail(@RequestBody SheetDetailRequest sheetDetailRequest) {
        return ResponseEntity.ok(timeSheetService.addSheetDetail(sheetDetailRequest));
    }

    @DeleteMapping(value = "/removeSheetDetail")
    public ResponseEntity<Boolean> removeSheetDetail(@RequestParam("detailId") Long id) {
        return ResponseEntity.ok(timeSheetService.deleteSheetDetail(id));
    }

    @RequestMapping(value = "/checkSheet/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkSheet(@PathVariable Long userId) {
        return ResponseEntity.ok(timeSheetService.checkTimeSheet(userId));
    }

    @RequestMapping(value = "/updateSheetDetail", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateSheetDetail(@RequestBody SheetDetailRequest sheetDetailRequest, @RequestParam("detailId") Long detailId) {
        return ResponseEntity.ok(timeSheetService.updateSheetDetail(sheetDetailRequest, detailId));
    }

    @GetMapping(value = "/getLengthOfMonth")
    public ResponseEntity<Integer> getLengthOfMonth(@RequestParam("month") String month) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getLengthOfMonth(month));
    }

    @GetMapping(value = "/getHasSheet/{userId}")
    public ResponseEntity<Boolean> getHasSheet(@RequestParam("month") String month, @PathVariable Long userId) {
        return ResponseEntity.ok(timeSheetService.hasSheetThatMonth(month, userId));
    }

    @GetMapping(value = "/getUsersWithSheetThatMonth")
    public ResponseEntity<List<User>> getUsersWithSheetThatMonth(@RequestParam("month") String month, @RequestParam("fullName") String fullName) {
        return ResponseEntity.ok(timeSheetService.getUsersWithSheetThatMonth(month, fullName));
    }

    @GetMapping(value = "/getFillPercentageByMonthAndYear/{userId}")
    public ResponseEntity<Integer> getFillPercentageByMonth(@RequestParam("month") String month, @PathVariable Long userId, @RequestParam("year") Integer year) {
        return ResponseEntity.ok(timeSheetService.getFillPercentageByMonth(month, year, userId));
    }
}