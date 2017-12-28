package be.rmangels.filemanager.service;

import be.rmangels.filemanager.infrastructure.files.FileExplorer;
import be.rmangels.filemanager.infrastructure.model.DataFile;
import be.rmangels.filemanager.infrastructure.model.FileContent;
import be.rmangels.filemanager.infrastructure.model.ManagedFile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class FileService {

    @Inject
    private FileExplorer fileExplorer;

    public List<DataFile> findAll() {
        return newArrayList(DataFile.Builder.of().withFilename("losdfkfqo").build());
    }

    public List<ManagedFile> findAllInDirectory(List<String> directoryStructure) {
        return fileExplorer.findFilesInDirectory(directoryStructure);
    }

    public void saveFile(List<String> directoryStructure, FileContent uploadfile) {
        fileExplorer.saveFile(directoryStructure, uploadfile);
    }
}
