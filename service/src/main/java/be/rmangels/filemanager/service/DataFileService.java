package be.rmangels.filemanager.service;

import be.rmangels.filemanager.infrastructure.model.DataFile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class DataFileService {
    public List<DataFile> findAll() {
        return newArrayList(DataFile.Builder.of("lolo").build());
    }
}
