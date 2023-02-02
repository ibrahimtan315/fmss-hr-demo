package com.fmss.hr.controllers.user;

import com.fmss.hr.common.ApiResponse;
import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.services.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/candidates/{candidateId}/files")
@AllArgsConstructor
@Api(value = "File Upload Controller")
public class FileUploadController {
    //TODO pathvariable candidateid kısmında bir mantık uyuşmazlığı var onu çöz
    @Autowired
    FileStorageService storageService;

    @PostMapping(value ="/upload" /*consumes = MediaType.APPLICATION_PDF_VALUE*/) //TODO pdf ile sınırlamak lazım
    @ApiOperation("Upload a file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file , String candidateName) {
        String message = "";
        try {
            storageService.save(file, candidateName);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        catch (Exception e) {
            message = "Could not upload the file: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping(value ="/view/{fileName:.+}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource file = storageService.getFileByName(fileName);

        if (file != null)
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}