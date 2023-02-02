package com.fmss.hr.controllers.user;

import com.fmss.hr.common.ApiResponse;
import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.SuggestDto;
import com.fmss.hr.query.CandidateQuery;
import com.fmss.hr.services.FileStorageService;
import com.fmss.hr.services.user.impl.CandidateServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/candidates")
@Api(value = "Candidate Controller")
@CrossOrigin("*")
public class CandidateController {
    private final CandidateServiceImpl candidateServiceImpl;
    private final CandidateQuery candidateQuery;
    private final FileStorageService storageService;

    @RequestMapping(value = "/{userId}/suggest/{advertId}", method=RequestMethod.POST,
            produces=MediaType.APPLICATION_JSON_VALUE,
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Suggest candidate")
    public ResponseEntity<CandidateDto> suggestCandidate(@RequestPart("candidate") SuggestDto suggestDto, @RequestPart("file") MultipartFile file, @PathVariable("advertId") Long advertId, @PathVariable("userId") Long userId) {
        if(!file.isEmpty()) {
            String cvPath = storageService.save(file, suggestDto.getLastName() + "-" +  suggestDto.getFirstName());
            CandidateDto candidateDto = candidateServiceImpl.suggestCandidate(suggestDto, cvPath, advertId, userId);
            if(candidateDto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(candidateDto);            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    }

    @GetMapping
    @ApiOperation(value = "List all candidates")
    public ResponseEntity<List<CandidateDto>> getAllCandidates() {
        List<CandidateDto> candidateDtoList = candidateServiceImpl.getAllCandidates();
        if (!candidateDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(candidateDtoList);        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    }

    @GetMapping("/list/{advertId}")
    @ApiOperation(value = "List specific candidates by advert")
    public ResponseEntity<List<CandidateDto>> getCandidatesFilteredByAdvert( @PathVariable("advertId") Long advertId) {
        List<CandidateDto> candidateDtoList = candidateServiceImpl.getAllByAdvert(advertId);
        if(! candidateDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(candidateDtoList);        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete candidate")
    public ResponseEntity<Boolean> deleteCandidate(Long id) {
        if (!candidateServiceImpl.deleteCandidateById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(false);        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(true);    }

    @PutMapping("/update/{candidateId}")
    @ApiOperation(value = "Update candidate")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable("candidateId") Long candidateId, @RequestPart("candidate") CandidateDto candidateDto) {
        CandidateDto updatedCandidateDto = candidateServiceImpl.updateCandidate(candidateId, candidateDto);
        if (updatedCandidateDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedCandidateDto);    }

    @GetMapping("/files/getAll")
    public ResponseEntity<List<String>> getAllFiles() {
        List<String> files = storageService.getAllFiles();
        if(!files.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(files);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}

   @GetMapping(value="/{candidateId}/fileView", produces = MediaType.APPLICATION_PDF_VALUE)
   public ResponseEntity<Resource> getCandidateCv(@PathVariable("candidateId") Long candidateId) { //TODO ApiResponse'a çevrilemedi
        Resource file = storageService.getFileByPath(candidateServiceImpl.getCandidateFilePath(candidateId));
        if(file.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"").body(file);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

   @PutMapping("/update/{candidateId}/fileUpdate") //TODO bu direkt id ve file alıp üstüne yazıyor. İleride kullanıcılara özel klasör olursa bozulur
    public ResponseEntity<ApiResponse<String>> updateFile(@PathVariable("candidateId") Long candidateId, @RequestPart("file") MultipartFile file) {
       if(!file.isEmpty()) {
           return ResponseEntity.ok(new ApiResponse<>(storageService.save(file, candidateServiceImpl.getCandidateById(candidateId).getLastName()
                   + "-" +  candidateServiceImpl.getCandidateById(candidateId).getFirstName()), GenericMessages.FILE_SUCCESSFULLY_UPDATED, HttpStatus.OK.value()));
       }
       return new ResponseEntity<>(new ApiResponse<>(null, GenericMessages.COULD_NOT_UPDATE_FILE, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
   }

   @PostMapping("/list-candidate-by-parameters")
    public ResponseEntity<ApiResponse<List<CandidateDto>>> listCandidatesByParameters(@RequestBody CandidateDto candidateDto) {
        List<CandidateDto> candidateDtoList = candidateQuery.ListCandidateByParameters(candidateDto);
        return ResponseEntity.ok(new ApiResponse<>(candidateDtoList,GenericMessages.CANDIDATES_LISTED,HttpStatus.OK.value()));
   }
    @GetMapping("/{candidateId}")
    public ResponseEntity<ApiResponse<CandidateDto>> getByCandidateId(@PathVariable Long candidateId){
        CandidateDto candidate = candidateServiceImpl.getCandidateById(candidateId);
        if(candidate != null){

            return ResponseEntity.ok(new ApiResponse<>(candidate,GenericMessages.CANDIDATES_LISTED, HttpStatus.OK.value()));
        }
        return new ResponseEntity<>(new ApiResponse<>(null,GenericMessages.CANDIDATE_NOT_FOUND,HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/numberOfCandidates")
    public ResponseEntity<Integer> numberOfCandidates(){
        return ResponseEntity.ok(candidateServiceImpl.numberOfCandidates());
    }
}