package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.PicDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FileProcess {


    public ArrayList<PicDto> fileSave(ArrayList <MultipartFile> am,String twPicCat,int twseq) {
        String path="C:\\project\\img";
        int size = 10 * 1024 * 1024;
        ArrayList<PicDto> picDtoArrayList = new ArrayList<>();
        PicDto picDto=null;
        for(int i=0;i<am.size();i++){
            if(am.get(i).getOriginalFilename()==null || am.get(i) ==null || am.get(i).isEmpty()) continue;
             picDto= new PicDto();
            String uuid = UUID.randomUUID().toString();
            String saveName=uuid+am.get(i).getOriginalFilename();
            File file = new File(path,saveName);
            picDto.setTwPicOri(am.get(i).getOriginalFilename());
            picDto.setTwPicSer(saveName);
            picDto.setTwPicCat(twPicCat);
            picDto.setTwSeq(twseq);
            picDtoArrayList.add(picDto);
            // if(!file.isDirectory())file.mkdir();
            try {
                am.get(i).transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return picDtoArrayList;

    }
}