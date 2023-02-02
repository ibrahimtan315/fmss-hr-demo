
package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.ExpenseDto;
import com.fmss.hr.dto.projection.ExpenseProjection;
import com.fmss.hr.dto.projection.ExpenseUserProjection;
import com.fmss.hr.entities.Expense;
import com.fmss.hr.repos.admin.ExpenseRepository;
import com.fmss.hr.services.admin.impl.ExpenseServiceImpl;
import com.fmss.hr.services.user.ExpenseStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseServiceImpl expenseServiceImpl;
    private final ExpenseRepository expenseRepository;
    private final ExpenseStorageService expenseStorageService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ExpenseDto>> getAllExpenseByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseServiceImpl.getAllExpenseByUserId(userId));
    }

    @RequestMapping(value = "expenceUserPageable/{userId}", method = RequestMethod.GET)
    public Page<ExpenseUserProjection> userPagination(@PathVariable Long userId,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return expenseRepository.userPageable(userId, pageable);
    }

    @GetMapping("/expencePageable")
    public Page<ExpenseProjection> pagination(@RequestParam Integer pageSize,
                                              @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return expenseRepository.pagination(pageable);
    }

    @GetMapping("getfindAllExpenceUsers")
    public ResponseEntity<List<ExpenseDto>> getfindAllExpenseUsers() {
        return ResponseEntity.ok(expenseServiceImpl.getFindAllExpenseUsers());
    }

    @GetMapping("/expenceTotal")
    public long pagination(Pageable pageable) {
        return expenseRepository.findAll(pageable).getTotalElements();
    }

    @RequestMapping(value = "/create-expense", method = RequestMethod.POST)
    public ResponseEntity<ExpenseDto> createExpense(@RequestPart("expense") ExpenseDto expenseDto,
                                                    @RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = expenseStorageService.uploadImage(file,expenseServiceImpl.createExpense(expenseDto));
        return ResponseEntity.ok(expenseDto);
    }

    @GetMapping("downloadExpense/{expenseId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long expenseId){
        byte[] imageData=expenseStorageService.downloadImage(expenseId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PutMapping(value = "approve-expense/{expenseId}")
    public ResponseEntity<ExpenseDto> approveExpense(@PathVariable("expenseId") Long id){
        return ResponseEntity.ok(expenseServiceImpl.approveExpense(id));
    }

    @PutMapping(value = "reject-expense/{expenseId}")
    public ResponseEntity<ExpenseDto> rejectExpense(@PathVariable("expenseId") Long id){
        return ResponseEntity.ok(expenseServiceImpl.rejectExpense(id));
    }

    @GetMapping(value ="pending-expenses/{managerId}")
    public ResponseEntity<List<ExpenseDto>> getPendingExpenses(@PathVariable("managerId") Long managerId){
        return ResponseEntity.ok(expenseServiceImpl.getPendingExpensesByManagerId(managerId));
    }
}
