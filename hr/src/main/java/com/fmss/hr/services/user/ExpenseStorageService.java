package com.fmss.hr.services.user;

import com.fmss.hr.dto.ExpenseDto;
import com.fmss.hr.entities.ExpenceImageData;
import com.fmss.hr.entities.Expense;
import com.fmss.hr.mapper.ExpenseMapper;
import com.fmss.hr.repos.user.ExpenceStorageRepository;
import com.fmss.hr.utils.ExpenceImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseStorageService {


    private final ExpenceStorageRepository repository;

    private final ExpenseMapper expenseMapper;

    public String uploadImage(MultipartFile file, ExpenseDto expenseDto) throws IOException {
        Expense expense = expenseMapper.expenseDtotoExpense(expenseDto);
        ExpenceImageData imageData = repository.save(ExpenceImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ExpenceImageUtils.compressImage(file.getBytes()))
                .expense(expense)
                .build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional
    public byte[] downloadImage(Long expenseId){
        Optional<ExpenceImageData> dbImageData = repository.findByExpenseId(expenseId);
        byte[] images=ExpenceImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
