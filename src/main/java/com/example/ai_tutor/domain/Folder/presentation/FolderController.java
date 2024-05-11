package com.example.ai_tutor.domain.Folder.presentation;

import com.example.ai_tutor.domain.Folder.application.FolderService;
import com.example.ai_tutor.domain.Folder.dto.request.FolderCreateReq;
import com.example.ai_tutor.global.config.security.token.CurrentUser;
import com.example.ai_tutor.global.payload.ErrorResponse;
import com.example.ai_tutor.global.payload.Message;
import com.example.ai_tutor.global.payload.ResponseCustom;
import com.sun.security.auth.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folder")
@Tag(name = "Folder", description = "폴더 관련 API입니다.")
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "폴더 생성 API", description = "폴더를 생성하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "새 폴더 생성 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "새 폴더 생성 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/")
    public ResponseCustom<?> createNewFolder(
            @Parameter @CurrentUser UserPrincipal userPrincipal,
            @RequestBody FolderCreateReq folderCreateReq
            )
    {
        String folderName = folderCreateReq.getFolderName();
        String professor = folderCreateReq.getProfessor();
        if(userPrincipal == null)
            return ResponseCustom.BAD_REQUEST("로그인이 필요합니다.");
        else if(folderName == null || professor == null){
            return ResponseCustom.BAD_REQUEST("폴더 이름과 교수 이름을 입력해주세요.");
        }
        else{folderService.createNewFolder(userPrincipal, folderCreateReq);}
        return ResponseCustom.OK();
    }



}
