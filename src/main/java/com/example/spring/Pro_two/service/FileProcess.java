package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FileProcess {


    public void fileSave(ArrayList <MultipartFile> am, MemberDto memberDto) {
        String path="C:\\project\\img";
        int size = 10 * 1024 * 1024;
        for(int i=0;i<am.size();i++){
            if(am.get(i)==null)continue;
            String uuid = UUID.randomUUID().toString();
            String saveName=uuid+am.get(i).getOriginalFilename();
            File file = new File(path,saveName);
            if(!file.isDirectory())file.mkdir();
            try {
                am.get(i).transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}